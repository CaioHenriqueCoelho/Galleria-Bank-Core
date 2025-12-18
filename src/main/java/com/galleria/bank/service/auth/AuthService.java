package com.galleria.bank.service.auth;

import com.galleria.bank.dto.login.LoginRequestDTO;
import com.galleria.bank.dto.login.LoginResponseDTO;
import com.galleria.bank.entity.user.UserEntity;
import com.galleria.bank.exception.ApiException;
import com.galleria.bank.exception.InvalidCredentialsException;
import com.galleria.bank.repository.user.UserRepository;
import com.galleria.bank.service.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository repository,
                       PasswordEncoder encoder,
                       JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        UserEntity user = repository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new ApiException("Login/Senha Inválidos.", HttpStatus.UNAUTHORIZED));


        new ApiException("Login/Senha Inválidos.", HttpStatus.UNAUTHORIZED);

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ApiException("Login/Senha Inválidos.", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getLogin()
        );

        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                token
        );
    }
}
