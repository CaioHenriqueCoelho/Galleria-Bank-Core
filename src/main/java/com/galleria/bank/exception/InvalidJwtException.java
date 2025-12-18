package com.galleria.bank.exception;

public class InvalidJwtException extends JwtAuthenticationException {

    public InvalidJwtException() {
        super("Token inválido");
    }
}