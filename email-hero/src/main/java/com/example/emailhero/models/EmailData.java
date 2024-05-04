package com.example.emailhero.models;

import java.util.Date;

public class EmailData {
    private String nonProfitEmail;
    private String emailMessage;
    private Date date;

    public EmailData(String nonProfitEmail, String emailMessage, Date date) {
        this.nonProfitEmail = nonProfitEmail;
        this.emailMessage = emailMessage;
        this.date = date;
    }

    public String getNonProfitEmail() {
        return nonProfitEmail;
    }

    public void setNonProfitEmail(String nonProfitEmail) {
        this.nonProfitEmail = nonProfitEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }
}
