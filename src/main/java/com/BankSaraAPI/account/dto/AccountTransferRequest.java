package com.BankSaraAPI.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransferRequest {

    private UUID senderId;
    private UUID receiverId;
    private double amount;

//    public UUID getSenderId() {
//        return senderId;
//    }
//
//    public void setSenderId(UUID senderId) {
//        this.senderId = senderId;
//    }
//
//    public UUID getReceiverId() {
//        return receiverId;
//    }
//
//    public void setReceiverId(UUID receiverId) {
//        this.receiverId = receiverId;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
}
