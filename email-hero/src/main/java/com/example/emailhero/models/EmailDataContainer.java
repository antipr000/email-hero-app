package com.example.emailhero.models;

import java.util.ArrayList;
import java.util.List;

public class EmailDataContainer {
    private final ArrayList<EmailData> allEmails;

    public EmailDataContainer() {
        this.allEmails = new ArrayList<>();
    }

    public void addEmail(EmailData newEmailData) {
        this.allEmails.add(newEmailData);
    }

    public ArrayList<EmailData> getAllEmails() {
        return this.allEmails;
    }
}
