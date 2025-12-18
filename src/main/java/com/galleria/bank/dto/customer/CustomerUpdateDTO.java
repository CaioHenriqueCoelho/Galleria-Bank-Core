package com.galleria.bank.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class CustomerUpdateDTO {

    @NotBlank
    @Size(min = 3)
    private String name;

    @NotBlank
    @CPF
    private String cpf;

    private String phone;

}
