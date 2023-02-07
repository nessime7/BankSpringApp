package com.BankSpringApp.card.service;

import com.BankSpringApp.card.dto.CreateCardRequest;
import com.BankSpringApp.account.model.Account;
import com.BankSpringApp.card.model.Card;
import com.BankSpringApp.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.BankSpringApp.card.model.CardType.PAYMENT;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void addCardToAccount(Account account, CreateCardRequest createCardRequest) {
        final var card = new Card(
                account.getId(),
                createCardRequest.getName(),
                createCardRequest.getSurname(),
                createCardRequest.getMaxLimit(),
                createCardRequest.getMaxDebet(),
                createCardRequest.getCardType());
        if (createCardRequest.getCardType().equals(PAYMENT)) {
            card.setMaxDebet(0);
        }
        cardRepository.save(card);
    }

    public void deleteCard(UUID cardId) {
        cardRepository.deleteById(cardId);
    }

}
