package com.example.emailhero.services;

import com.example.emailhero.domain.PaginatedResponse;
import com.example.emailhero.domain.SendEmailRequestDTO;
import com.example.emailhero.exceptions.BaseException;
import com.example.emailhero.exceptions.CsvReadException;
import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.exceptions.InvalidEmailTemplateException;
import com.example.emailhero.models.EmailData;
import com.example.emailhero.models.GrantRecord;
import com.example.emailhero.models.NonProfit;
import com.example.emailhero.repository.EmailTemplateRepository;
import com.example.emailhero.repository.FoundationRepository;
import com.example.emailhero.repository.GrantsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoundationService {
    private  final Logger logger = LoggerFactory.getLogger(FoundationService.class);

    @Autowired
    private FoundationRepository foundationRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTemplateProcessor emailTemplateProcessor;

    @Autowired
    private CsvService csvReaderService;

    @Autowired
    private GrantsRepository grantsRepository;

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
            logger.info("Template is not valid");
            throw new InvalidEmailTemplateException("Email template provided is not valid!");
        }
        emailTemplateRepository.addEmailTemplate(email, template);
    }

    public String getEmailTemplate(String email) throws DataNotFoundException {
        return emailTemplateRepository.getEmailTemplate(email);
    }

    public ArrayList<String> sendEmail(String email, SendEmailRequestDTO requestDTO)
            throws DataNotFoundException {
        List<String> nonProfitEmails = requestDTO.getNonProfitEmails();
        String template = emailTemplateRepository.getEmailTemplate(email);
        ArrayList<String> failureEmails = new ArrayList<>();
        for (String nonProfitEmail: nonProfitEmails) {
            try {
                NonProfit nonProfit = foundationRepository.getNonProfitForFoundationByEmail(email, nonProfitEmail);
                emailService.sendEmail(template, nonProfit, email);
            } catch (DataNotFoundException e) {
                logger.error("No non profit found for email: {}", nonProfitEmail);
                failureEmails.add(nonProfitEmail);
            }
        }

        return failureEmails;
    }

    public ArrayList<EmailData> getAllEmails(String email) throws DataNotFoundException {
        return emailService.getAllEmails(email);
    }

    public PaginatedResponse<GrantRecord> getAllGrantRecords(String email, int pageOffset, int numRecords)
            throws BaseException {
        return grantsRepository.getGrantRecords(email, pageOffset, numRecords);
    }

    public void uploadCsvFile(String foundationEmail, MultipartFile file) throws BaseException {
        String filePath = csvReaderService.uploadCsvFile(file);
        List<GrantRecord> data = csvReaderService.getGrantRecords(filePath);
        grantsRepository.insertGrantRecords(foundationEmail, data);
    }
}
