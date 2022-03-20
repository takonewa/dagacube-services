package com.rank.dagacube.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString(of="id")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String transactionId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
