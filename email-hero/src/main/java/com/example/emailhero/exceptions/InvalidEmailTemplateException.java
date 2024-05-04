package com.example.emailhero.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidEmailTemplateException extends BaseException {
    public InvalidEmailTemplateException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}