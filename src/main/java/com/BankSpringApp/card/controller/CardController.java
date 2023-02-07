package com.BankSpringApp.card.controller;


import com.BankSpringApp.card.dto.CreateCardRequest;
import com.BankSpringApp.account.service.AccountService;
import com.BankSpringApp.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/*
1)
accounts/:id/cards

2)
cards

 */
@RestController
public class CardController {

    private final CardService cardService;
    private final AccountService accountService;

    @Autowired
    public CardController(CardService cardService, AccountService accountService) {
        this.cardService = cardService;
        this.accountService = accountService;
    }

    @PostMapping("accounts/{accountId}/cards")
    public ResponseEntity<Void> addCard(@Valid @PathVariable("accountId") UUID accountId, @RequestBody CreateCardRequest createCardRequest) {
        final var account = accountService.findAccount(accountId);
        cardService.addCardToAccount(account, createCardRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("cards/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable("cardId") UUID cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.noContent().build();
    }
}
