package com.example.emailhero.domain;

import com.example.emailhero.models.NonProfit;

import java.util.List;

public class SendEmailRequestDTO {
    private List<NonProfit> nonProfits;

    public SendEmailRequestDTO() {}

    public SendEmailRequestDTO(List<NonProfit> nonProfits) {
        this.nonProfits = nonProfits;
    }

    public List<NonProfit> getNonProfits() {
        return nonProfits;
    }

    public void setNonProfits(List<NonProfit> nonProfits) {
        this.nonProfits = nonProfits;
    }
}
