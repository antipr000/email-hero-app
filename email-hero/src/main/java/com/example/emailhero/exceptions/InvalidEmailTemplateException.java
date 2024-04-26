package com.example.emailhero.exceptions;

public class InvalidEmailTemplateException extends Exception {
    public InvalidEmailTemplateException(String errorMessage) {
        super(errorMessage);
    }
}