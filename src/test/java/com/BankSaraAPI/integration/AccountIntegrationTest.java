package com.BankSaraAPI.integration;

import com.BankSaraAPI.BankSaraApplication;
import com.BankSaraAPI.TestUtils;
import com.BankSaraAPI.account.model.Account;
import com.BankSaraAPI.account.model.CurrencyType;
import com.BankSaraAPI.account.repository.AccountRepository;
import com.BankSaraAPI.card.model.Card;
import com.BankSaraAPI.card.model.CardType;
import com.BankSaraAPI.card.repository.CardRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(classes = {BankSaraApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountIntegrationTest {

    @LocalServerPort
    private int port;
    private static final String CONTEXT = "account";

    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        accountRepository.save(new Account(UUID.fromString("fc35ad28-91fc-449b-af3e-918417266f9d"), "main account", 10000.10, CurrencyType.PLN));
        accountRepository.save(new Account(UUID.fromString("5fd82e4e-c0ae-4771-a9d5-e18e3df32d65"), "savings account", 1, CurrencyType.PLN));
        accountRepository.save(new Account(UUID.fromString("5f73cec7-6ac1-46ee-a203-794c35d8800c"), "EUR account", 50, CurrencyType.EUR));
        accountRepository.save(new Account(UUID.fromString("3d9c2f62-d66c-4e31-89a7-ccdf271a7591"), "USD account", 50, CurrencyType.USD));
        accountRepository.save(new Account(UUID.fromString("e62ee6a0-4549-4956-a0dd-685f23526961"), "CHF account", 50, CurrencyType.CHF));
        accountRepository.save(new Account(UUID.fromString("7b93e505-2c0f-47a6-8ad6-6f6125c1c9a3"), "GBP account", 50, CurrencyType.GBP));
        accountRepository.save(new Account(UUID.fromString("c7c6c077-931e-42f9-982a-8c836ab6b932"), "GBP account", 250.5, CurrencyType.PLN));

        cardRepository.save(new Card(UUID.fromString("ac3a7a5a-1949-4182-b2ca-7f138bd19915"), UUID.fromString("fc35ad28-91fc-449b-af3e-918417266f9d"), "Sara", "Przebinda", 100.0, 100.0, CardType.CREDIT));
        cardRepository.save(new Card(UUID.fromString("71781a31-ed7f-4127-9734-aa4c23cb74a7"), UUID.fromString("fc35ad28-91fc-449b-af3e-918417266f9d"), "Sara", "Przebinda", 100.0, 100.0, CardType.PAYMENT));
        cardRepository.save(new Card(UUID.fromString("5e6e4432-1723-4e77-80d5-70cf1f83c80a"), UUID.fromString("5f73cec7-6ac1-46ee-a203-794c35d8800c"), "Albert", "Podraza", 100.0, 100.0, CardType.PAYMENT));
        cardRepository.save(new Card(UUID.fromString("bd8bcbc5-0a0b-47d0-8af9-2afc824a3a96"), UUID.fromString("5f73cec7-6ac1-46ee-a203-794c35d8800c"), "Albert", "Podraza", 100.0, 100.0, CardType.CREDIT));
    }

    @Test
    void should_return_all_accounts() throws IOException {
        given()
                .when().get("/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("", equalTo(TestUtils.getPath("response/get-all-accounts-response.json", CONTEXT).get("")));
    }

    @Test
    void should_add_new_account_when_currency_is_not_provided() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/add-new-account-request-without-currency.json", CONTEXT))
                .when().post("/accounts")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void should_add_new_account_with_EUR_currency_when_currency_is_not_provided() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/add-new-account-request-with-EUR-currency.json", CONTEXT))
                .when().post("/accounts")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void should_change_the_balance_in_savings_from_0_to_1000() throws IOException {
        var id = "5fd82e4e-c0ae-4771-a9d5-e18e3df32d65";
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/change-the-balance.json", CONTEXT))
                .when().put(String.format("/accounts/%s/balance", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("", equalTo(TestUtils.getPath("response/change-the-balance-of-savings.json", CONTEXT).get("")));
    }

    @Test
    void should_change_the_currency_from_PLN_to_EUR() throws IOException {
        var id = "fc35ad28-91fc-449b-af3e-918417266f9d";
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/change-the-currency.json", CONTEXT))
                .when().put(String.format("/accounts/%s/currency", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("", equalTo(TestUtils.getPath("response/change-the-currency-of-main.json", CONTEXT).get("")));
    }

    @Test
    void should_delete_GBP_account() {
        var id = "c7c6c077-931e-42f9-982a-8c836ab6b932";
        given().contentType(ContentType.JSON)
                .when().delete("/accounts/{%s}", id)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void should_transfer_50_from_EUR_account_to_USD_account() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void should_not_transfer_when_id_is_incorrect() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer-incorrect-id.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Autowired
    AccountRepository accountRepository;

    @Test
    void should_not_transfer_when_balance_is_under_zero() throws IOException {
        // given

        // then
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer-incorrect-balance.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .and().body("message", is("Account balance is too low"));
    }

    @Test
    void should_not_transfer_when_amount_is_under_zero() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer-incorrect-amount.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .and().body("message", is("Cannot be less than zero"));
    }

    @Test
    void should_not_transfer_20_from_GBP_account_to_CHF_account() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer-incorrect-GBP-to-CHF.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .and().body("message", is("Transfer is not possible"));
    }

}

