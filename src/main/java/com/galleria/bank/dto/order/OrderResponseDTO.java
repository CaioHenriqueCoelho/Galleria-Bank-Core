package com.galleria.bank.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {

    private Long id;
    private String customerName;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> items;

    public OrderResponseDTO(
            Long id,
            String customerName,
            BigDecimal total,
            LocalDateTime createdAt,
            List<OrderItemResponseDTO> items
    ) {
        this.id = id;
        this.customerName = customerName;
        this.total = total;
        this.createdAt = createdAt;
        this.items = items;
    }
}

