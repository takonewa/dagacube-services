package com.rank.dagacube.web.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class TransactionDTO implements Serializable {
    private double amount;
    private String description;
    private String transactionId;
    private Date transactionDate;
}
