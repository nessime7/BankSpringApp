package com.BankSpringApp.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

// @ControllerAdvice to specjalizacja adnotacji @Component,
// która umożliwia obsługę wyjątków w całej aplikacji w jednym globalnym komponencie obsługi.
// Może być postrzegany jako przechwytywacz wyjątków rzucanych przez metody z adnotacjami @RequestMapping i podobnymi.

// parsowanie - zmiana formy

@ControllerAdvice
public class ResponseExceptionHandler
        extends ResponseEntityExceptionHandler {

    // @ExceptionHandler(BookNotFoundException. class) – która pozwala nasłuchiwanie, kiedy pojawi się podany wyjątek i wówczas podjąć inicjatywę
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                // Nagłówek HTTP to pole żądania lub odpowiedzi HTTP,
                // które przekazuje dodatkowy kontekst i metadane dotyczące żądania lub odpowiedzi .
                // Na przykład komunikat żądania może używać nagłówków do wskazania preferowanych formatów multimediów,
                // podczas gdy odpowiedź może używać nagłówka do wskazywania formatu multimediów zwróconej treści.
                Map.of("message", ex.getMessage()), new HttpHeaders(), SC_INTERNAL_SERVER_ERROR);
        // 422 Nieprzetwarzalny podmiot
        // Kod stanu odpowiedzi protokołu HTTP (HyperText Transfer Protocol) 422 Unprocessable Entity wskazuje,
        // że serwer rozumie typ zawartości jednostki żądania, a składnia jednostki żądania jest poprawna,
        // ale nie mógł przetworzyć zawartych instrukcji.
    }
}