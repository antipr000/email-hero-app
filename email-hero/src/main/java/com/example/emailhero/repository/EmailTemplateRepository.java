package com.example.emailhero.repository;

import com.example.emailhero.db.Database;
import com.example.emailhero.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmailTemplateRepository {
    @Autowired
    private Database<String> emailTemplateDatabase;

    public void addEmailTemplate(String foundationEmail, String newTemplate) {
        emailTemplateDatabase.put(foundationEmail, newTemplate);
    }

    public String getEmailTemplate(String foundationEmail) throws DataNotFoundException {
        return emailTemplateDatabase.get(foundationEmail);
    }
}
