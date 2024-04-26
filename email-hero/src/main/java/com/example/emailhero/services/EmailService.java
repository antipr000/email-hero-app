package com.example.emailhero.services;

import com.example.emailhero.models.NonProfit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private EmailTemplateProcessor emailTemplateProcessor;

    public void sendEmail(String template, NonProfit nonProfit) {
        String finalEmail = emailTemplateProcessor.applyTemplate(template, nonProfit);

        // We don't need to send the email, printing it instead
        logger.info("\n============== Email begins =========================\n");

        logger.info(finalEmail);

        logger.info("\n============== Email ends   =========================\n");
    }
}
