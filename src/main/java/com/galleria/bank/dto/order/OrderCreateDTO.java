package com.galleria.bank.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class OrderCreateDTO {

    @NotNull
    private Long customerId;

    @NotEmpty
    private List<OrderItemCreateDTO> items;
}

