package com.nilson.account.request;

import javax.validation.constraints.NotNull;

public class IndividualPersonRequest {

    @NotNull
    private String cpf;
    @NotNull
    private String fullName;
    @NotNull
    private String birthDay;

    public String getCpf() {
        return cpf;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBirthDay() {
        return birthDay;
    }

}
