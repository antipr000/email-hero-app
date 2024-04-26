package com.example.emailhero.services;

import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.models.NonProfit;
import com.example.emailhero.repository.FoundationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoundationService {
    @Autowired
    private FoundationRepository foundationRepository;

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
}
