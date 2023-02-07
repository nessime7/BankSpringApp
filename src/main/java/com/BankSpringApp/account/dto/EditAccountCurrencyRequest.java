package com.BankSpringApp.account.dto;

import com.BankSpringApp.account.model.CurrencyType;

public class EditAccountCurrencyRequest {
    private CurrencyType currency;

    public EditAccountCurrencyRequest(CurrencyType currency) {
        this.currency = currency;
    }

    public EditAccountCurrencyRequest() {
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }
}
