package com.BankSaraAPI.account.dto;

import com.BankSaraAPI.account.model.CurrencyType;

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
