package com.example.bank_app.operations.processors;

import com.example.bank_app.account.AccountService;
import com.example.bank_app.operations.ConsoleOperationType;
import com.example.bank_app.operations.OperationCommandProcessor;
import com.example.bank_app.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;
    public CreateAccountProcessor(
            Scanner scanner,
            UserService userService,
            AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter user id for creation account");
        int userId = Integer.parseInt(scanner.nextLine());
        var user = userService.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user with id %s"
                        .formatted(userId)));
        var account = accountService.createAccount(user);
        user.getAccountList().add(account);
        System.out.println("New account created with id %s for user %s"
                .formatted(account.getId(), user.getLogin()));

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
