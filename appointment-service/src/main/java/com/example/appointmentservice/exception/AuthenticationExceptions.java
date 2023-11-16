package com.example.appointmentservice.exception;

public class AuthenticationExceptions extends RuntimeException{
    public AuthenticationExceptions(String message) {
        super(message);
    }
}
