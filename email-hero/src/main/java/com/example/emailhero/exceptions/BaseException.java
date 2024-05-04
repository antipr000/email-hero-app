package com.example.emailhero.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends Exception {
    public BaseException(String errorMessage) {
        super(errorMessage);
    }

    public abstract HttpStatus getHttpStatusCode();
}
