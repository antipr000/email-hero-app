package com.example.emailhero.exceptions;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends BaseException {
    public DataNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}