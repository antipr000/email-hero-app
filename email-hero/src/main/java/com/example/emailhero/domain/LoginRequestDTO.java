package com.example.emailhero.domain;

public class LoginRequestDTO {
    private String email;

    public LoginRequestDTO(String email) {
        this.email = email;
    }

    public LoginRequestDTO() {}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
