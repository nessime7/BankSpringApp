package com.BankSaraAPI.account.controller;

import com.BankSaraAPI.account.model.Account;
import com.BankSaraAPI.account.dto.AccountTransferRequest;
import com.BankSaraAPI.account.dto.CreateAccountRequest;
import com.BankSaraAPI.account.dto.EditAccountBalanceRequest;
import com.BankSaraAPI.account.dto.EditAccountCurrencyRequest;
import com.BankSaraAPI.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/*
- account management
    - get/create/edit/delete account
    - transfer between accounts
 GET/POST/PUT/DELETE
 */

/*
1. Baza lokalna - postgres
branch -> PR -> merge -> budowanie programu na devowym srodowisu(git clone, gradle build, gradle bootRun,
po tym program widoczny na jakims porcie)
2. Baza devowa - postgres
GET 131.123.123.123/api/accounts
3. Baza produkcyjna
GET 131.123.123.223/api/accounts

 */
@RestController
public class AccountController {

    private final AccountService accountService;

    // dependency injection
    // jak będzie widział tą klasę to musi stworzyć sam tą klasę
    // gdzieś tam pod spodem, konstrukt taki, który nam stworzy tą klasę
    // BankController z accountService
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // wyświetlenie wszystkich kont
    @GetMapping("accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    // stworzenie nowego konta
    @PostMapping("accounts")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);
        return ResponseEntity.noContent().build();
    }

    // edit balance
    @PutMapping("accounts/{id}/balance")
    public ResponseEntity<Account> editAccount(@PathVariable UUID id, @RequestBody EditAccountBalanceRequest request) {
        final var account = accountService.editAccount(id, request);
        return ResponseEntity.ok(account);
    }

    // edit currency
    @PutMapping("accounts/{id}/currency")
    public ResponseEntity<Account> editCurrency(@PathVariable UUID id, @RequestBody EditAccountCurrencyRequest request) {
        final var account = accountService.changeCurrency(id, request);
        return ResponseEntity.ok(account);
    }

    // delete id
    @DeleteMapping("accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    // transfer
    @PostMapping("transfers")
    public ResponseEntity<Void> editAccount(@RequestBody AccountTransferRequest request) throws SQLException {
        accountService.transfer(request);
        return ResponseEntity.noContent().build();
    }

}
