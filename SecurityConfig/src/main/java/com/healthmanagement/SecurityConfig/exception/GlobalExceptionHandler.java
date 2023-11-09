package com.healthmanagement.SecurityConfig.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExists.class) //It used in specific Exception
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExists(EmailAlreadyExists emailAlreadyExists,
                                                                     WebRequest webRequest)
    {
        ErrorDetails exceptionDetails=new ErrorDetails(
                LocalDateTime.now(),
                emailAlreadyExists.getMessage(),
                webRequest.getDescription(false),
                "Email is already Taken"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationExceptions.class) //It used in specific Exception
    public ResponseEntity<ErrorDetails> handleAuthenticationError(AuthenticationExceptions authenticationExceptions,
                                                                  WebRequest webRequest)
    {
        ErrorDetails exceptionDetails=new ErrorDetails(
                LocalDateTime.now(),
                authenticationExceptions.getMessage(),
                webRequest.getDescription(false),
                "Authentication Failed"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

}
