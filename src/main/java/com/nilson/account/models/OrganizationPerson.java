package com.nilson.account.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class OrganizationPerson extends Person {

    @NotNull
    protected String cnpj;

    @NotNull
    private String socialName;

    @NotNull
    private String fantasyName;

    public OrganizationPerson() {
    }

    public OrganizationPerson(@NotNull String cnpj, @NotNull String socialName, @NotNull String fantasyName) {
        super();
        this.cnpj = cnpj;
        this.socialName = socialName;
        this.fantasyName = fantasyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }
}
