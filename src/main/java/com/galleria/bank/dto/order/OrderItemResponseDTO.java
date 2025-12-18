package com.galleria.bank.dto.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDTO {

    private Long productId;
    private String description;
    private Integer quantity;
    private BigDecimal value;

    public OrderItemResponseDTO(
            Long productId,
            String description,
            Integer quantity,
            BigDecimal value
    ) {
        this.productId = productId;
        this.description = description;
        this.quantity = quantity;
        this.value = value;
    }

}
