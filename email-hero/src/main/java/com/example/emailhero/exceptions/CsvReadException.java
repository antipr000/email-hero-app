package com.example.emailhero.exceptions;

import org.springframework.http.HttpStatus;

public class CsvReadException extends BaseException {
    public CsvReadException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}