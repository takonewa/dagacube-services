package com.rank.dagacube.web;

import com.rank.dagacube.exceptions.*;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log
@Controller
@ControllerAdvice
public class ExceptionsResource extends ResponseEntityExceptionHandler {

    private static final String REASON_HEADER = "reason";

    @ExceptionHandler({Exception.class})
    public ResponseEntity handle(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(500)
                .headers(headers -> headers.add(REASON_HEADER, exception.getMessage()))
                .build();
    }

    @ExceptionHandler({PlayerNotFoundException.class})
    public ResponseEntity handlePlayerNotFoundException() {
        return ResponseEntity.notFound()
                .headers(headers -> headers.add(REASON_HEADER, "The specified player does not exist"))
                .build();
    }

    @ExceptionHandler({InsufficientFundsException.class})
    public ResponseEntity handleInsufficientFundsException() {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                .headers(headers -> headers.add(REASON_HEADER, "The player does not have enough funds"))
                .build();
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    public ResponseEntity handleInsufficientAuthenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .headers(headers -> headers.add(REASON_HEADER, "Invalid credentials provided"))
                .build();
    }

    @ExceptionHandler({InvalidPromoCodeException.class})
    public ResponseEntity handleInvalidPromoCodeException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(headers -> headers.add(REASON_HEADER, "Invalid promo code provided"))
                .build();
    }

    @ExceptionHandler({InvalidAmountException.class})
    public ResponseEntity handleInvalidAmountException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .headers(headers -> headers.add(REASON_HEADER, "Transaction amount should be positive"))
                .build();
    }

    @GetMapping("/")
    public String index() {
        return ("redirect:/swagger-ui.html");
    }
}
