package com.galleria.bank.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateDTO {

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private BigDecimal value;

}
