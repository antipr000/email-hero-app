package com.example.emailhero.repository;

import com.example.emailhero.db.Database;
import com.example.emailhero.db.InMemoryDatabaseImpl;
import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.models.FoundationData;
import com.example.emailhero.models.NonProfit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.List;

@Repository
public class FoundationRepository {
    Logger logger = LoggerFactory.getLogger(FoundationRepository.class);
    private final Database<FoundationData> nonProfitDatabase;

    public FoundationRepository() {
        logger.info("Called constructor of FoundationRepository");
        nonProfitDatabase = new InMemoryDatabaseImpl<FoundationData>();
    }

    public void addNewFoundation(String email) {
        nonProfitDatabase.put(email, new FoundationData());
    }

    public boolean checkIfFoundationExists(String email) {
        return nonProfitDatabase.containsKey(email);
    }

    public void addNonProfitOrganisation(String foundationEmail, NonProfit nonProfit) throws DataNotFoundException {
        FoundationData data = nonProfitDatabase.get(foundationEmail);
        data.addNonProfit(nonProfit);
        nonProfitDatabase.put(foundationEmail, data);
    }

    public List<NonProfit> getAllNonProfitsForFoundation(String foundationEmail) throws DataNotFoundException {
        FoundationData data = nonProfitDatabase.get(foundationEmail);
        return data.getAllNonProfits();
    }

    public NonProfit getNonProfitForFoundationByEmail(String foundationEmail, String nonProfitEmail)
            throws DataNotFoundException {
        FoundationData data = nonProfitDatabase.get(foundationEmail);
        return data.getNonProfitByEmail(nonProfitEmail);
    }

    public void removeNonProfitForFoundation(String foundationEmail, String nonProfitEmail)
            throws DataNotFoundException {
        FoundationData data = nonProfitDatabase.get(foundationEmail);
        data.removeNonProfit(nonProfitEmail);
    }
}
