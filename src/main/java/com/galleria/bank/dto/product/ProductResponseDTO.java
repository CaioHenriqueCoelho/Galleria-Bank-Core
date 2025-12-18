package com.galleria.bank.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {

    private Long id;
    private String description;
    private BigDecimal value;

    public ProductResponseDTO(Long id, String descricao, BigDecimal valor) {
        this.id = id;
        this.description = descricao;
        this.value = valor;
    }

}
