package com.example.emailhero.controllers;

import com.example.emailhero.domain.LoginRequestDTO;
import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.models.NonProfit;
import com.example.emailhero.services.FoundationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

}
