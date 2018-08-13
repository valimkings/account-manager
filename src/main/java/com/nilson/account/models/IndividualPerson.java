package com.nilson.account.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class IndividualPerson extends Person {

    @NotNull
    protected String cpf;

    @NotNull
    private String fullName;


        private String birthDay;

    public IndividualPerson() {
    }

    public IndividualPerson(@NotNull String cpf, @NotNull String fullName, String birthDay) {
        super();
        this.cpf = cpf;
        this.fullName = fullName;
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "IndividualPerson{" +
                "cpf='" + cpf + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", id=" + id +
                '}';
    }
}
