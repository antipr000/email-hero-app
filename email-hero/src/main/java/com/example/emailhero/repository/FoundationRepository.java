package com.example.emailhero.repository;

import com.example.emailhero.db.Database;
import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.models.FoundationData;
import com.example.emailhero.models.NonProfit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoundationRepository {
    @Autowired
    private Database<FoundationData> nonProfitDatabase;

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

    public void removeNonProfitForFoundation(String foundationEmail, String nonProfitEmail)
            throws DataNotFoundException {
        FoundationData data = nonProfitDatabase.get(foundationEmail);
        data.removeNonProfit(nonProfitEmail);
    }
}
