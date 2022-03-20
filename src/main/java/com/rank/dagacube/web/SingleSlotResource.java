package com.rank.dagacube.web;

import com.rank.dagacube.exceptions.*;
import com.rank.dagacube.web.dto.*;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.rank.dagacube.service.SingleSlotService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "SingleSlotResource", description = "Simplified API for single slot gaming machine")
public class SingleSlotResource {

    private final SingleSlotService service;

    @GetMapping("/balance/{playerId}")
    public ResponseEntity<BalanceDTO> currentBalance(@PathVariable Long playerId) throws PlayerNotFoundException {
        return ResponseEntity.ok(service.getPlayerBalance(playerId));
    }

    @PostMapping("/wager")
    public ResponseEntity deductMoney(@Valid @RequestBody TransactionRequestDTO request) throws InsufficientFundsException, PlayerNotFoundException, InvalidAmountException {
        if (!service.transactionExists(request.getTransactionId()))
            service.deductMoney(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity depositMoney(@Valid @RequestBody TransactionRequestDTO request) throws PlayerNotFoundException, InvalidAmountException {
        if (!service.transactionExists(request.getTransactionId()))
            service.depositMoney(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> playerTransactions(@Valid @RequestBody PlayerTransactionsRequestDTO request) throws PlayerNotFoundException, InsufficientAuthenticationException {
        return ResponseEntity.ok(service.playerTransaction(request));
    }

    @PostMapping("/bonus")
    public ResponseEntity<Void> bonusSetup(@Valid @RequestBody BonusRequestDTO request) throws InvalidPromoCodeException {
        service.setupBonus(request);
        return ResponseEntity.noContent().build();
    }
}
