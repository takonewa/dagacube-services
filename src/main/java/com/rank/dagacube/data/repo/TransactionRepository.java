package com.rank.dagacube.data.repo;

import com.rank.dagacube.data.model.Account;
import org.springframework.data.domain.Sort;
import com.rank.dagacube.data.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Set<Transaction> findAllByAccount(Account account, Sort sort);
    Optional<Transaction> findByTransactionId(String transactionId);
}
