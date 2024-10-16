package com.example.bank_app.operations.processors;

import com.example.bank_app.account.AccountService;
import com.example.bank_app.operations.ConsoleOperationType;
import com.example.bank_app.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TransferAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final AccountService accountService;

    public TransferAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter source account id:");
        int fromAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter destination account id:");
        int toAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to transfer");
        int amountToTransfer= Integer.parseInt(scanner.nextLine());
        accountService.transfer(fromAccountId, toAccountId, amountToTransfer);
        System.out.println("Successfully transferred %s from accId %s to accId %s"
                .formatted(amountToTransfer, fromAccountId, toAccountId));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
