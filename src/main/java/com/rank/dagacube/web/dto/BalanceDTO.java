package com.rank.dagacube.web.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BalanceDTO implements Serializable {
    private double currentBalance;
}
