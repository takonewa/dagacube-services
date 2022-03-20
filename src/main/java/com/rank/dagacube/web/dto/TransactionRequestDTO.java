package com.rank.dagacube.web.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class TransactionRequestDTO implements Serializable {
    @NotNull(message = "Player ID is a mandatory field")
    private long playerId;
    @NotNull(message = "Transaction ID is a mandatory field")
    @NotBlank(message = "Unique Transaction ID is required")
    private String transactionId;
    @NotNull(message = "Amount is a mandatory field")
    @Positive(message = "Amount should be greater than 0")
    private Double amount;
}
