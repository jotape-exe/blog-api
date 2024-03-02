package com.jorgedev.creditapp.service.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class StringSizeException extends RuntimeException {
    public StringSizeException(String message) {
        super(message);
    }
}
