package com.example.bank_app.operations.processors;

import com.example.bank_app.account.Account;
import com.example.bank_app.account.AccountService;
import com.example.bank_app.operations.ConsoleOperationType;
import com.example.bank_app.operations.OperationCommandProcessor;
import com.example.bank_app.user.User;
import com.example.bank_app.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CloseAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final AccountService accountService;
    private final UserService userService;

    public CloseAccountProcessor(Scanner scanner,
                                 AccountService accountService,
                                 UserService userService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account id to close:");
        int accountId = Integer.parseInt(scanner.nextLine());
        Account account = accountService.closeAccount(accountId);

        User user = userService.findUserById(account.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("No such user with id = %s"
                        .formatted(account.getUserId())));
        user.getAccountList().remove(account);
        System.out.println("Account successfuly closed Id %s"
                .formatted(accountId));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
