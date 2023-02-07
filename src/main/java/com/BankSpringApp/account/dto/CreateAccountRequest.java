package com.BankSpringApp.account.dto;

import com.BankSpringApp.account.model.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    @NotBlank
    private String name;
    @NotBlank
    private double balance;
    private CurrencyType currency;

}
