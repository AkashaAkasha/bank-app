package com.example.bank_app.operations.processors;

import com.example.bank_app.account.AccountService;
import com.example.bank_app.operations.ConsoleOperationType;
import com.example.bank_app.operations.OperationCommandProcessor;
import com.example.bank_app.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DepositAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final AccountService accountService;

    public DepositAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account id:");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to deposit");
        int amountToDeposit = Integer.parseInt(scanner.nextLine());
        accountService.depositAccount(accountId, amountToDeposit);
        System.out.println("Successfuly deposit amount %s to account Id %s"
                .formatted(amountToDeposit, accountId));

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
