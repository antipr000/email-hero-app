package com.example.emailhero.exceptions;

import org.springframework.http.HttpStatus;

public class FileUploadException extends BaseException {
    public FileUploadException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
