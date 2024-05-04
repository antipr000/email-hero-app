package com.example.emailhero.models;

import com.example.emailhero.decorators.CsvColumn;
import com.example.emailhero.domain.GrantStages;
import com.example.emailhero.domain.GrantTypes;

import java.util.ArrayList;
import java.util.List;

public class GrantRecord {
    @CsvColumn(index = 0)
    public String nonProfitName;

    @CsvColumn(index=1)
    public String grantSubmissionName;

    @CsvColumn(index=2)
    public GrantStages stage;

    @CsvColumn(index=4)
    public String requestedAmount;

    @CsvColumn(index=5)
    public String awardedAmount;

    @CsvColumn(index=6)
    public GrantTypes grantType;

    @CsvColumn(index=7)
    public List<String> tags;

    @CsvColumn(index=8)
    public String startDate;

    @CsvColumn(index=9)
    public String endDate;

    public GrantRecord() {}

    public String getNonProfitName() {
        return nonProfitName;
    }

    public void setNonProfitName(String nonProfitName) {
        this.nonProfitName = nonProfitName;
    }

    public String getGrantSubmissionName() {
        return grantSubmissionName;
    }

    public void setGrantSubmissionName(String grantSubmissionName) {
        this.grantSubmissionName = grantSubmissionName;
    }

    public GrantStages getStage() {
        return stage;
    }

    public void setStage(GrantStages stage) {
        this.stage = stage;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getAwardedAmount() {
        return awardedAmount;
    }

    public void setAwardedAmount(String awardedAmount) {
        this.awardedAmount = awardedAmount;
    }

    public GrantTypes getGrantType() {
        return grantType;
    }

    public void setGrantType(GrantTypes grantType) {
        this.grantType = grantType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
