package com.nilson.account.request;

import javax.validation.constraints.NotNull;

public class ContributionReverseRequest {

    @NotNull
    private String transactionCode;

    public String getTransactionCode() {
        return transactionCode;
    }

}
