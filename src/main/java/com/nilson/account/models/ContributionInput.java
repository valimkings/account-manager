package com.nilson.account.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ContributionInput {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne
    private Account destinationAccount;

    @Column(unique = true, nullable = false)
    private String transactionCode;

    private boolean reversed;

    @NotNull
    private Double amount;

    public ContributionInput(Account destinationAccount, String uuid, Double amount) {
        this.destinationAccount = destinationAccount;
        this.transactionCode = uuid;
        this.amount = amount;
        this.reversed = false;
    }

    public ContributionInput() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public boolean isReversed() {
        return reversed;
    }


}
