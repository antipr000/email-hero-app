package com.example.emailhero.domain;

import com.example.emailhero.models.NonProfit;

import java.util.List;

public class SendEmailRequestDTO {
    private List<String> nonProfitEmails;

    public SendEmailRequestDTO() {}

    public SendEmailRequestDTO(List<String> nonProfitEmails) {
        this.nonProfitEmails = nonProfitEmails;
    }

    public List<String> getNonProfitEmails() {
        return nonProfitEmails;
    }

    public void setNonProfitEmails(List<String> nonProfitEmails) {
        this.nonProfitEmails = nonProfitEmails;
    }
}
