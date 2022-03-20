package com.rank.dagacube.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PlayerTransactionsRequestDTO implements Serializable {
    @NotNull(message = "Player ID is  mandatory field")
    private Long playerId;
    @NotNull(message = "password is a mandatory field")
    @NotBlank(message = "password is a mandatory field")
    private String password;
}
