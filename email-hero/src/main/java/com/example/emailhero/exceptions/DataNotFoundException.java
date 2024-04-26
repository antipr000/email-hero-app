package com.example.emailhero.exceptions;

public class DataNotFoundException extends Exception {
    public DataNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}