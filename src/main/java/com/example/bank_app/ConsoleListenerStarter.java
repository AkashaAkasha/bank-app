package com.example.bank_app;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ConsoleListenerStarter {
    private final OperationConsoleListener consoleListener;
    private Thread consoleListenerThread;

    public ConsoleListenerStarter(OperationConsoleListener consoleListener) {
        this.consoleListener = consoleListener;
    }

    @PostConstruct
    private void postConstruct(){
        this.consoleListenerThread = new Thread(() -> {
            consoleListener.srart();
            consoleListener.listenUpdates();
        });
        consoleListenerThread.start();
    }

    @PreDestroy
    public void preDestroy() {
        consoleListenerThread.interrupt();
        consoleListener.endListen();
    }
}
