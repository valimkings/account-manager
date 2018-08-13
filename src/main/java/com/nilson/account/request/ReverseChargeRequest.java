package com.nilson.account.request;

import javax.validation.constraints.NotNull;

public class ReverseChargeRequest {

    @NotNull
    private Long transactionId;


    public Long getTransactionId() {
        return transactionId;
    }

}
