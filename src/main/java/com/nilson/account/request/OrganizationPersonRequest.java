package com.nilson.account.request;

import javax.validation.constraints.NotNull;

public class OrganizationPersonRequest {

    @NotNull
    private String cnpj;
    @NotNull
    private String fantasyName;
    @NotNull
    private String socialName;

    public String getCnpj() {
        return cnpj;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public String getSocialName() {
        return socialName;
    }
}
