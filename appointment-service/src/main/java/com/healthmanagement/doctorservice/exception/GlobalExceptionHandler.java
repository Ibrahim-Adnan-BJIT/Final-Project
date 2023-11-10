package com.healthmanagement.doctorservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


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
