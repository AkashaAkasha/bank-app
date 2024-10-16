package com.example.bank_app.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final int defaultAccountAmount;
    private final double transferCommission;

    public AccountProperties(
            @Value("${accoount.default-amount}") int defaultAccountAmount,
            @Value("${account.transfer-commision}") double transferCommission) {
        this.defaultAccountAmount = defaultAccountAmount;
        this.transferCommission = transferCommission;
    }

    public int getDefaultAccountAmount() {
        return defaultAccountAmount;
    }

    public double getTransferCommission() {
        return transferCommission;
    }
}
