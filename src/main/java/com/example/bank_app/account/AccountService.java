package com.example.bank_app.account;

import com.example.bank_app.user.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
    private final Map<Integer, Account> accountMap;
    private int idCounter;
    private final AccountProperties accountProperties;

    public AccountService(AccountProperties accountProperties) {
        this.accountMap = new HashMap<>();
        this.idCounter = 0;
        this.accountProperties = accountProperties;
    }

    public Account createAccount(User user) {
        idCounter++;
        Account account = new Account(idCounter, user.getId(), accountProperties.getDefaultAccountAmount());//defolt money
        accountMap.put(account.getId(), account);
        return account;
    }

    public Optional<Account> findAccountById(int id) {
        return Optional.ofNullable(accountMap.get(id));
    }

    public List<Account> getAllUserAccount(int userId) {
        return accountMap.values()
                .stream()
                .filter(account -> account.getUserId() == userId)
                .toList();
    }

    public void depositAccount(int accountId, int moneyToDeposit) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No account with id = %s".formatted(accountId)));
        if (moneyToDeposit <= 0) {
            throw new IllegalArgumentException("Can not deposit not positive amount %s"
                    .formatted(moneyToDeposit));
        }
        account.setMoneyAmount(account.getMoneyAmount() + moneyToDeposit);
    }

    public void withdrawFromAccount(int accountId, int amountToWithdtaw) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No account with id = %s".formatted(accountId)));
        if (amountToWithdtaw <= 0) {
            throw new IllegalArgumentException("Can not withdraw not positive amount %s"
                    .formatted(amountToWithdtaw));
        }
        if (account.getMoneyAmount() < amountToWithdtaw) {
            throw new IllegalArgumentException("Can not withdraw from account id %s MoneyAmount %s amountToWithdtaw %s"
                    .formatted(accountId, account.getMoneyAmount(), amountToWithdtaw));
        }
        account.setMoneyAmount(account.getMoneyAmount() - amountToWithdtaw);
    }

    public Account closeAccount(int accountId) {
        var accountToRemove = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No account with id = %s".formatted(accountId)));
        List<Account> accountList = getAllUserAccount(accountToRemove.getUserId());
        if (accountList.size() == 1) {
            throw new IllegalArgumentException("Cannot close the only one account");

        }
        Account accountToDeposit = accountList.stream()
                .filter(it -> it.getId() != accountId)
                .findFirst()
                .orElseThrow();
        accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
        accountMap.remove(accountId);
        return accountToRemove;
    }

    public void transfer(int fromAccountId, int toAccountId, int amountToTransfer) {
        var accountFrom = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No account with id = %s".formatted(fromAccountId)));
        var accountTo = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No account with id = %s".formatted(toAccountId)));
        if (amountToTransfer <= 0) {
            throw new IllegalArgumentException("Can not withdraw not positive amount %s"
                    .formatted(amountToTransfer));
        }
        if (accountFrom.getMoneyAmount() < amountToTransfer) {
            throw new IllegalArgumentException("Can not transfer from account id %s MoneyAmount %s amountToTransfer %s"
                    .formatted(accountFrom, accountFrom.getMoneyAmount(), amountToTransfer));
        }

        int totalAmountToDeposit = accountTo.getUserId() != accountFrom.getUserId()
                ? (int) (amountToTransfer - amountToTransfer * accountProperties.getTransferCommission())
                : amountToTransfer;
        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - amountToTransfer);
        accountTo.setMoneyAmount(accountTo.getMoneyAmount() - totalAmountToDeposit);
    }
}
