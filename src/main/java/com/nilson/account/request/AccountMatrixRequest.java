package com.nilson.account.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountMatrixRequest {

    @NotNull
    private String name;

    @NotNull
    private String accountType;

    @NotNull
    private Long personId;

    public String getName() {
        return name;
    }

    public String getAccountType() {
        return accountType;
    }

    public Long getPersonId() {
        return personId;
    }

}
