package com.example.emailhero.services;

import com.example.emailhero.domain.SendEmailRequestDTO;
import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.exceptions.InvalidEmailTemplateException;
import com.example.emailhero.models.NonProfit;
import com.example.emailhero.repository.EmailTemplateRepository;
import com.example.emailhero.repository.FoundationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoundationService {
    @Autowired
    private FoundationRepository foundationRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTemplateProcessor emailTemplateProcessor;

    public NonProfit addNonProfit(String email, NonProfit nonProfit) throws DataNotFoundException {
        foundationRepository.addNonProfitOrganisation(email, nonProfit);
        return nonProfit;
    }

    public List<NonProfit> getAllNonProfits(String email) throws DataNotFoundException {
        return foundationRepository.getAllNonProfitsForFoundation(email);
    }

    public void loginOrSignup(String email) {
        if (!foundationRepository.checkIfFoundationExists(email)) {
            foundationRepository.addNewFoundation(email);
        }
    }

    public void addEmailTemplate(String email, String template) throws InvalidEmailTemplateException {
        if (!emailTemplateProcessor.validTemplate(template, NonProfit.class)) {
            throw new InvalidEmailTemplateException("Email template provided is not valid!");
        }
        emailTemplateRepository.addEmailTemplate(email, template);
    }

    public String getEmailTemplate(String email) throws DataNotFoundException {
        return emailTemplateRepository.getEmailTemplate(email);
    }

    public void sendEmail(String email, SendEmailRequestDTO requestDTO) throws DataNotFoundException {
        List<NonProfit> nonProfits = requestDTO.getNonProfits();
        String template = emailTemplateRepository.getEmailTemplate(email);
        for (NonProfit nonProfit: nonProfits) {
            emailService.sendEmail(template, nonProfit);
        }
    }
}
