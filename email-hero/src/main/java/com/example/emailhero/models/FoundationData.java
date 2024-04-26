package com.example.emailhero.models;

import com.example.emailhero.exceptions.DataNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FoundationData {
    private final Map<String, NonProfit> nonProfitMap;

    public FoundationData() {
        nonProfitMap = new TreeMap<>();
    }

    public void addNonProfit(NonProfit nonProfit) {
        String email = nonProfit.getEmail();
        nonProfitMap.put(email, nonProfit);
    }

    public NonProfit getNonProfitByEmail(String email) throws DataNotFoundException {
        if (!nonProfitMap.containsKey(email)) {
            throw new DataNotFoundException("No non profit with given email exists!");
        }
        return nonProfitMap.get(email);
    }

    public List<NonProfit> getAllNonProfits() {
        return nonProfitMap.values().stream().toList();
    }

    public void removeNonProfit(String email) throws DataNotFoundException {
        if (!nonProfitMap.containsKey(email)) {
            throw new DataNotFoundException("No non profit with given email exists!");
        }
        nonProfitMap.remove(email);
    }
}
