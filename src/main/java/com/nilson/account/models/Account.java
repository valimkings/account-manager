package com.nilson.account.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account {

    private Double balance;
    private String name;
    private AccountStatus accountState;
    private AccountType accountType;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @OneToOne
    @JoinColumn
    private Person person;

    @ManyToOne()
    @JoinColumn
    private Account parentAccount;

    public Account(){ }

    public Account(String name, AccountType accountType, Person person) {
        this.name = name;
        this.accountType = accountType;
        this.person = person;
        this.balance = 0.0;
        this.accountState = AccountStatus.ACTIVE;
    }

    public Account(String name, AccountType accountType, Person person, Account parentAccount) {
        this.balance = 0.0;
        this.name = name;
        this.accountState = AccountStatus.ACTIVE;
        this.accountType = accountType;
        this.person = person;
        this.parentAccount = parentAccount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountStatus getAccountState() {
        return accountState;
    }

    public void setAccountState(AccountStatus accountState) {
        this.accountState = accountState;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Account getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(Account parentAccount) {
        this.parentAccount = parentAccount;
    }

    public boolean isActive() {
        return this.accountState.equals(AccountStatus.ACTIVE);
    }

    public boolean isMatrix(){
        return this.accountType.equals(AccountType.MATRIX);
    }

    public void debitAmount(Double amount) {
        this.balance -= amount;
    }

    public void credit(Double amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", name='" + name + '\'' +
                ", accountState=" + accountState +
                ", accountType=" + accountType +
                ", id=" + id +
                ", person=" + person +
                ", parentAccount=" + parentAccount +
                '}';
    }
}
