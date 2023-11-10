package com.healthmanagement.doctorservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRoomNumber extends  RuntimeException{

    public InvalidRoomNumber(String message) {
        super(message);
    }
}
