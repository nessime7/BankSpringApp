package com.BankSpringApp.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ResponseEntity<String> handleError() {
        // Handle the error and return a response
        String errorMessage = "This is a custom error message.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}