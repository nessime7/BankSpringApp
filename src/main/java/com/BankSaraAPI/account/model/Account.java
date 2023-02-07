package com.BankSaraAPI.account.model;

import com.BankSaraAPI.card.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.UUID;

// obiekt danej klasy reprezentuje rekord w tabeli w bazie danych
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Account {

    @Id
    @Column(name = "id")
    private UUID id;
    @NotBlank
    private String name;

    @Positive
    private double balance;
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", updatable = false, insertable = false)
    private List<Card> cards;

    public Account(String name, double balance, CurrencyType currency) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    public Account(UUID id, String name, double balance, CurrencyType currency) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }
}
