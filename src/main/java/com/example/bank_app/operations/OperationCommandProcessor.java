package com.example.bank_app.operations;

public interface OperationCommandProcessor {
    void processOperation();
    ConsoleOperationType getOperationType();
}
