package com.example.emailhero.repository;

import com.example.emailhero.db.Database;
import com.example.emailhero.db.InMemoryDatabaseImpl;
import com.example.emailhero.exceptions.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EmailTemplateRepository {
    Logger logger = LoggerFactory.getLogger(EmailTemplateRepository.class);
    private final Database<String> emailTemplateDatabase;

    public EmailTemplateRepository() {
        logger.info("Called constructor of EmailTemplateRepository");
        emailTemplateDatabase = new InMemoryDatabaseImpl<String>();
    }

    public void addEmailTemplate(String foundationEmail, String newTemplate) {
        emailTemplateDatabase.put(foundationEmail, newTemplate);
    }

    public String getEmailTemplate(String foundationEmail) throws DataNotFoundException {
        return emailTemplateDatabase.get(foundationEmail);
    }
}
