package com.rank.dagacube.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BonusRequestDTO implements Serializable {
    @NotNull(message = "A secrete code is mandatory")
    @NotBlank(message = "A secrete code is mandatory")
    private String code;
}
