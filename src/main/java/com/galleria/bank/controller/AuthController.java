package com.galleria.bank.controller;

import com.galleria.bank.dto.login.LoginRequestDTO;
import com.galleria.bank.dto.login.LoginResponseDTO;
import com.galleria.bank.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        return ResponseEntity.ok(service.login(dto));
    }
}

