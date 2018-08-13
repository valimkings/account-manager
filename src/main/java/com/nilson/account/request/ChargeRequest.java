package com.nilson.account.request;

import javax.validation.constraints.NotNull;

public class ChargeRequest {

    @NotNull
    private Long destinationAccountId;

    @NotNull
    private Double amount;

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public Double getAmount() {
        return amount;
    }
}
