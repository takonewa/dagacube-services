package com.rank.dagacube.service;

import com.rank.dagacube.data.model.Account;
import com.rank.dagacube.data.model.Player;
import com.rank.dagacube.data.model.Transaction;
import com.rank.dagacube.data.model.TransactionType;
import com.rank.dagacube.data.repo.AccountRepository;
import com.rank.dagacube.data.repo.PlayerRepository;
import com.rank.dagacube.data.repo.TransactionRepository;
import com.rank.dagacube.exceptions.*;
import com.rank.dagacube.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.by;

@Log
@Service
@RequiredArgsConstructor
public class SingleSlotService {
    @Value("${info.app.password}")
    private String password;
    @Value("${info.app.promo-code}")
    private String promoCode;
    private final AtomicInteger bonusHolder;
    private final PlayerRepository playerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public List<TransactionDTO> playerTransaction(PlayerTransactionsRequestDTO requestDTO) throws PlayerNotFoundException, InsufficientAuthenticationException {
        if (!Arrays.equals(password.toCharArray(), requestDTO.getPassword().toCharArray())) {
            throw new InsufficientAuthenticationException();
        }
        Player player = getPlayer(requestDTO.getPlayerId());
        Set<Transaction> transactions = transactionRepository.findAllByAccount(player.getAccount(), by("id").descending());
        return (transactions.stream()
                .limit(10)
                .map(e -> TransactionDTO
                        .builder()
                        .amount(e.getAmount())
                        .transactionId(e.getTransactionId())
                        .transactionDate(e.getTransactionDate())
                        .description(e.getTransactionType().name())
                        .build())
                .collect(Collectors.toList()));
    }

    public BalanceDTO getPlayerBalance(Long playerId) throws PlayerNotFoundException {
        Player player = getPlayer(playerId);
        return BalanceDTO.builder()
                .currentBalance(player.getAccount().getBalance())
                .build();
    }

    @Transactional
    public void deductMoney(TransactionRequestDTO request) throws InsufficientFundsException, PlayerNotFoundException, InvalidAmountException {
        Player player = getPlayer(request.getPlayerId());
        /*if (request.getAmount() <= 0) {
            throw new InvalidAmountException();
        }*/
        Account account = player.getAccount();
        if (bonusHolder.get() <= 0 && !account.hasEnoughFunds(request.getAmount())) {
            throw new InsufficientFundsException();
        }
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionDate(new Date());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionId(request.getTransactionId());
        if (bonusHolder.get() > 0) {
            bonusHolder.decrementAndGet();
            transaction.setTransactionType(TransactionType.PROMO_WITHDRAWAL);
        } else {
            transaction.setTransactionType(TransactionType.WITHDRAWAL);
            account.setBalance(BigDecimal.valueOf(account.getBalance()).subtract(BigDecimal.valueOf(request.getAmount())).doubleValue());
        }
        transactionRepository.save(transaction);
        accountRepository.save(account);
    }

    @Transactional
    public void depositMoney(TransactionRequestDTO request) throws PlayerNotFoundException, InvalidAmountException {
        Player player = getPlayer(request.getPlayerId());
        /*if (request.getAmount() <= 0) {
            throw new InvalidAmountException();
        }*/
        Account account = player.getAccount();
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(TransactionType.WINNING);
        transaction.setTransactionId(request.getTransactionId());
        transactionRepository.save(transaction);
        account.setBalance(BigDecimal.valueOf(account.getBalance()).add(BigDecimal.valueOf(request.getAmount())).doubleValue());
        accountRepository.save(account);
    }

    public void setupBonus(BonusRequestDTO request) throws InvalidPromoCodeException {
        if (!Arrays.equals(promoCode.toCharArray(), request.getCode().toCharArray())) {
            throw new InvalidPromoCodeException();
        }
        bonusHolder.set(5);
    }

    public boolean transactionExists(String transactionId) {
        Optional<Transaction> optionalTransaction = this.transactionRepository.findByTransactionId(transactionId);
        return (optionalTransaction.isPresent());
    }

    private Player getPlayer(Long playerId) throws PlayerNotFoundException {
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if (optionalPlayer.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        return optionalPlayer.get();
    }
}
