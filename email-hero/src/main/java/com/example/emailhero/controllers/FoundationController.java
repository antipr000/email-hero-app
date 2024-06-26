package com.example.emailhero.controllers;

import com.example.emailhero.domain.*;
import com.example.emailhero.exceptions.BaseException;
import com.example.emailhero.exceptions.CsvReadException;
import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.exceptions.InvalidEmailTemplateException;
import com.example.emailhero.models.EmailData;
import com.example.emailhero.models.GrantRecord;
import com.example.emailhero.models.NonProfit;
import com.example.emailhero.services.FoundationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class FoundationController {

    Logger logger = LoggerFactory.getLogger(FoundationController.class);

    @Autowired
    private FoundationService foundationService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDTO requestDTO) {
        String email = requestDTO.getEmail();
        logger.info("Received signup request: {}", email);
        foundationService.loginOrSignup(email);
    }

    @PostMapping("/nonprofit")
    public NonProfit addNonProfit(@RequestBody NonProfit nonProfit,
                                  @RequestHeader("Email") String email) {
        try {
            logger.info("Received add non profit request: {} from {}", nonProfit, email);
            return foundationService.addNonProfit(email, nonProfit);
        } catch (DataNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Foundation not found"
            );
        }
    }

    @GetMapping("/nonprofit")
    public List<NonProfit> getAllNonProfits(@RequestHeader("Email") String email) {
        try {
            return foundationService.getAllNonProfits(email);
        } catch (DataNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Foundation not found"
            );
        }
    }

    @PostMapping("/template")
    public void addEmailTemplate(@RequestBody AddEmailTemplateRequestDTO requestDTO,
                                 @RequestHeader("Email") String email) {
        try {
            logger.info("Received add email template request: {}", requestDTO.getTemplate());
            foundationService.addEmailTemplate(email, requestDTO.getTemplate());
        } catch (InvalidEmailTemplateException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email template is not valid"
            );
        }
    }

    @GetMapping("/template")
    public EmailTemplateResponseDTO getEmailTemplate(@RequestHeader("Email") String email) {
        try {
            String template = foundationService.getEmailTemplate(email);
            return new EmailTemplateResponseDTO(template);
        } catch (DataNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Email template does not exist"
            );
        }
    }

    @PostMapping("/sendemail")
    public SendEmailResponseDTO sendEmail(@RequestBody SendEmailRequestDTO requestDTO,
                          @RequestHeader("Email") String email) {
        try {
            ArrayList<String> failures = foundationService.sendEmail(email, requestDTO);
            return new SendEmailResponseDTO(failures);
        } catch (DataNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Email template does not exist"
            );
        }
    }

    @GetMapping("/emails")
    public ArrayList<EmailData> getAllEmails(@RequestHeader("Email") String email) {
        try{
            return foundationService.getAllEmails(email);
        } catch (DataNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No foundation record found!"
            );
        }
    }

    @GetMapping("/grant/records")
    public PaginatedResponse<GrantRecord> getAllGrantRecords(@RequestParam("pageOffset") int pageOffset,
                                                             @RequestParam("numRecords") int numRecords,
                                                             @RequestHeader("Email") String email) {
        try {
            return foundationService.getAllGrantRecords(email, pageOffset, numRecords);
        } catch (BaseException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(
                    e.getHttpStatusCode(),
                    e.getMessage()
            );
        }
    }

    @PostMapping("/grant/file")
    public void uploadGrantRecords(
            @RequestHeader("Email") String email,
            @RequestParam("file")MultipartFile file) {
        try {
            logger.info("File upload called for: {}", email);
            foundationService.uploadCsvFile(email, file);
        } catch (BaseException e) {
            throw new ResponseStatusException(
                    e.getHttpStatusCode(),
                    e.getMessage()
            );
        }
    }
}
