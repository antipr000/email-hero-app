package com.example.emailhero.services;

import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.models.EmailData;
import com.example.emailhero.models.NonProfit;
import com.example.emailhero.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmailService {
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private EmailTemplateProcessor emailTemplateProcessor;

    @Autowired
    private EmailRepository emailRepository;

    public void sendEmail(String template, NonProfit nonProfit, String foundationEmail)
            throws DataNotFoundException {
        String finalEmail = emailTemplateProcessor.applyTemplate(template, nonProfit);

        emailRepository.addEmailMessage(foundationEmail, nonProfit, finalEmail);

        // We don't need to send the email, printing it instead
        logger.info("\n============== Email begins =========================\n");

        logger.info(finalEmail);

        logger.info("\n============== Email ends   =========================\n");
    }

    public ArrayList<EmailData> getAllEmails(String foundationEmail) throws DataNotFoundException {
        return emailRepository.getAllEmails(foundationEmail);
    }
}
