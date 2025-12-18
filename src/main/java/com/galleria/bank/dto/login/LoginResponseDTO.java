package com.galleria.bank.dto.login;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private Long id;
    private String name;
    private String token;

    public LoginResponseDTO(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

}

