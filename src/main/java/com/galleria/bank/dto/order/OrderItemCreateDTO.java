package com.galleria.bank.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemCreateDTO {

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;

}
