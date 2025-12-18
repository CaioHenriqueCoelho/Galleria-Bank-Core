package com.galleria.bank.dto.customer;

import lombok.Data;

@Data
public class CustomerResponseDTO {

    private Long id;
    private String name;
    private String cpf;
    private String phone;

    public CustomerResponseDTO(Long id, String name, String cpf, String phone) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
    }

}
