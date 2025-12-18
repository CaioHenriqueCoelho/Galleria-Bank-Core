package com.galleria.bank.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @NotBlank
    @Size(min = 3)
    private String name;

    @NotBlank
    private String login;

    private String password;

}
