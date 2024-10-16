package com.example.bank_app;

import com.example.bank_app.operations.ConsoleOperationType;
import com.example.bank_app.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class OperationConsoleListener {

    private final Scanner scanner;
    private final Map<ConsoleOperationType, OperationCommandProcessor> processorMap;

    public OperationConsoleListener(
            Scanner scanner,
            List<OperationCommandProcessor> processorList) {
        this.scanner = scanner;
        this.processorMap = processorList
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        OperationCommandProcessor::getOperationType,
                                        processor -> processor
                                )
                        );
    }

    public void listenUpdates() {
        while (!Thread.currentThread().isInterrupted()) {
            var operationType = listenNewOperation();
            if(operationType == null) {return;}
            processNewOperation(operationType);
        }
    }

    private ConsoleOperationType listenNewOperation() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("\nPlease type operation:");
            printAllAvailableOperation();
            var nextOperation = scanner.nextLine();
            try {
                return ConsoleOperationType.valueOf(nextOperation);
            } catch (IllegalArgumentException e) {
                System.out.println("No such command found");
            }
        }
        return null;
    }

    private void printAllAvailableOperation() {
        processorMap.keySet()
                .forEach(System.out::println);
    }

    private void processNewOperation(ConsoleOperationType operation) {
        try {
            var processor = processorMap.get(operation);
            processor.processOperation();
        } catch (Exception e) {
            System.out.printf("Error executing command %s: error = %s%n", operation, e.getMessage());
        }
    }

    public void srart() {
        System.out.println("Console Listener started");
    }

    public void endListen() {
        System.out.println("Console Listener end listen");
    }
}
