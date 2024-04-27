package com.example.emailhero.domain;

import java.util.ArrayList;
import java.util.List;

public class SendEmailResponseDTO {
    private List<String> failureEmails;

    public SendEmailResponseDTO() {}

    public SendEmailResponseDTO(ArrayList<String> failureEmails) {
        this.failureEmails = failureEmails;
    }

    public List<String> getFailureEmails() {
        return failureEmails;
    }

    public void setFailureEmails(List<String> failureEmails) {
        this.failureEmails = failureEmails;
    }
}
