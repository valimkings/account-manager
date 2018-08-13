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

}
