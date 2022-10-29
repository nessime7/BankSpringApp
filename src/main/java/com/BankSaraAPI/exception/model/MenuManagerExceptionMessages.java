package com.BankSaraAPI.exception.model;

public final class MenuManagerExceptionMessages {

public static final String ACCOUNT_BALANCE_TOO_LOW = "Account balance is too low";
public static final String CANNOT_BE_LESS_THAN_ZERO = "Cannot be less than zero";
public static final String TRANSFER_IS_NOT_POSSIBLE = "Transfer is not possible";

private MenuManagerExceptionMessages(){
    throw new AssertionError("No MenuManagerExceptionMessages instances for you!");
}

}
