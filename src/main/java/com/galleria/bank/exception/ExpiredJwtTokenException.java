package com.galleria.bank.exception;

public class ExpiredJwtTokenException extends JwtAuthenticationException {
    public ExpiredJwtTokenException() {
        super("Token expirado");
    }
}
