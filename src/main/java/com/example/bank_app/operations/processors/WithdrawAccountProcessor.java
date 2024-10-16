package com.example.bank_app.operations.processors;

import com.example.bank_app.account.AccountService;
import com.example.bank_app.operations.ConsoleOperationType;
import com.example.bank_app.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class WithdrawAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final AccountService accountService;

    public WithdrawAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account id:");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to withdraw");
        int amountToWithdtaw= Integer.parseInt(scanner.nextLine());
        accountService.withdrawFromAccount(accountId, amountToWithdtaw);
        System.out.println("Successfuly withdrawn amount %s to account Id %s"
                .formatted(amountToWithdtaw, accountId));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
