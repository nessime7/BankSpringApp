package com.BankSaraAPI.service;

import com.BankSaraAPI.card.dto.CreateCardRequest;
import com.BankSaraAPI.account.model.Account;
import com.BankSaraAPI.card.model.Card;
import com.BankSaraAPI.card.model.CardType;
import com.BankSaraAPI.account.model.CurrencyType;
import com.BankSaraAPI.account.repository.AccountRepository;
import com.BankSaraAPI.card.repository.CardRepository;
import com.BankSaraAPI.card.service.CardService;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CardServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final CardRepository cardRepository = mock(CardRepository.class);
    private final CardService cardService = new CardService(cardRepository);

    @Test
    void should_add_a_card_to_account() {
        // given
        var accountId = "ba2958e6-1d88-4a8d-829f-af7113259505";
        var account = new Account(UUID.fromString(accountId), "nowe", 100, CurrencyType.PLN);
        var createCardRequest = new CreateCardRequest("Kasia", "Swarbula", 10, 10, CardType.CREDIT);
        var card = new Card(UUID.fromString(accountId), "Kasia", "Swarbula", 10, 10, CardType.CREDIT);
        given(cardRepository.save(any())).willReturn(card);
        given(accountRepository.findById(account.getId())).willReturn(Optional.of(account));

        // when
        cardService.addCardToAccount(account, createCardRequest);

        // then
        verify(cardRepository).save(any());
    }

    @Test
    void should_delete_card_from_account() {
        // given
        var account = new Account(UUID.fromString("ba2958e6-1d88-4a8d-829f-af7113259505"), "nowe", 100, CurrencyType.PLN);
        var card = new Card(UUID.fromString("ba2958e6-1d88-4a8d-829f-af7113259505"), "Kasia", "Swarbula", 10, 10, CardType.CREDIT);
        given(cardRepository.save(any())).willReturn(card);
        given(accountRepository.findById(account.getId())).willReturn(Optional.of(account));

        // when
        cardService.deleteCard(card.getId());

        // then
        then(cardRepository).should().deleteById(card.getId());
    }
}
