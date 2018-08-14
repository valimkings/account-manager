package com.nilson.account.controllers;

import com.nilson.account.models.Account;
import com.nilson.account.request.AccountMatrixRequest;
import com.nilson.account.request.AccountRequest;
import com.nilson.account.response.EntityCreated;
import com.nilson.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/matrix")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createAccountMatrix(@RequestBody @Valid AccountMatrixRequest accountRequest) {
        EntityCreated accountId = accountService.createAccount(accountRequest);
        return new ResponseEntity<>(accountId, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody @Valid AccountRequest accountRequest) {
        EntityCreated accountId = accountService.createMatrixAccount(accountRequest);
        return new ResponseEntity<>(accountId, HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping("(/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long accountId) {
        return new ResponseEntity<>(accountService.findById(accountId), HttpStatus.OK);
    }

}
