package com.galleria.bank.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateDTO {

    @NotBlank
    private String description;

    @NotNull
    @Positive
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal value;

}
