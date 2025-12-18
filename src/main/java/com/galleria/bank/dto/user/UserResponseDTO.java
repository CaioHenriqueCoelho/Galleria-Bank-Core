package com.galleria.bank.dto.user;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String login;

    public UserResponseDTO(Long id, String name, String login) {
        this.id = id;
        this.name = name;
        this.login = login;
    }
}
