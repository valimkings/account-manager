package com.nilson.account.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRequest extends AccountMatrixRequest {

    @NotNull
    private Long parentAccount;

    public Long getParentAccount() {
        return parentAccount;
    }

}
