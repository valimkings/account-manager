package com.nilson.account.services;

import com.nilson.account.exceptions.AccountTypeException;
import com.nilson.account.exceptions.ResourceNotFoundException;
import com.nilson.account.models.Account;
import com.nilson.account.models.AccountType;
import com.nilson.account.models.Person;
import com.nilson.account.repositories.AccountRepository;
import com.nilson.account.repositories.PersonRepository;
import com.nilson.account.request.AccountMatrixRequest;
import com.nilson.account.request.AccountRequest;
import com.nilson.account.request.Response.EntityCreated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AccountService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AccountRepository accountRepository;

    private static final Logger log = LogManager.getLogger(AccountService.class);

    public EntityCreated createAccount(AccountMatrixRequest accountRequest) {
        checkAccountType(accountRequest.getAccountType());

        Person person = getPerson(accountRequest);
        Account accountCreated = accountRepository.save(buildAccount(accountRequest, person));

        log.info(String.format("Conta criada com sucesso %s", accountCreated.toString()));

        return new EntityCreated(accountCreated.getId());
    }

    public EntityCreated createMatrixAccount(AccountRequest accountRequest) {
        checkAccountType(accountRequest.getAccountType());

        Account parentAccount = findById(accountRequest.getParentAccount());
        Person person = getPerson(accountRequest);
        Account accountCreated = accountRepository.save(buildMatrixAccount(accountRequest, person, parentAccount));

        log.info(String.format("Conta criada MATRIX com sucesso %s", accountCreated.toString()));

        return new EntityCreated(accountCreated.getId());
    }

    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Conta não encontrada com id: %s ", accountId)));

    }

    private Person getPerson(AccountMatrixRequest accountRequest) {
        return personRepository.findById(accountRequest.getPersonId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Pessoa não encontrada com id: %s ", accountRequest.getPersonId())));
    }

    private Account buildAccount(AccountMatrixRequest accountRequest, Person person) {
        return new Account(accountRequest.getName(), AccountType.valueOf(accountRequest.getAccountType()), person);
    }

    private Account buildMatrixAccount(AccountMatrixRequest accountRequest, Person person, Account parentAccount) {
        return new Account(accountRequest.getName(), AccountType.valueOf(accountRequest.getAccountType()), person, parentAccount);
    }

    private void checkAccountType(String accountType) {
        if (!Arrays.asList(AccountType.values()).toString().contains(accountType)) {
            throw new AccountTypeException(String.format("Conta do tipo %s é inválida", accountType));
        }
    }

}
