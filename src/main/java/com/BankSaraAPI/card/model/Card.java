package com.BankSaraAPI.card.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Card {
    @Id
    private UUID id;
    private UUID accountId;
    private String name;
    private String surname;
    private double maxLimit;
    private double maxDebet;
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    public Card(UUID id,UUID accountId, String name, String surname, double maxLimit, double maxDebet, CardType cardType) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.surname = surname;
        this.maxLimit = maxLimit;
        this.maxDebet = maxDebet;
        this.cardType = cardType;
    }
    public Card(UUID accountId, String name, String surname, double maxLimit, double maxDebet, CardType cardType) {
        this.id = UUID.randomUUID();
        this.accountId = accountId;
        this.name = name;
        this.surname = surname;
        this.maxLimit = maxLimit;
        this.maxDebet = maxDebet;
        this.cardType = cardType;
    }
}
