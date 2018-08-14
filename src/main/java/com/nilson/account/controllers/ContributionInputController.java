package com.nilson.account.controllers;

import com.nilson.account.models.ContributionInput;
import com.nilson.account.request.ContribuitionInputRequest;
import com.nilson.account.request.ContributionReverseRequest;
import com.nilson.account.response.EntityCreated;
import com.nilson.account.services.ContributionInputService;
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
public class ContributionInputController {

    @Autowired
    private ContributionInputService contribuitionInputService;

    @PostMapping("/contribution")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> transfer(@RequestBody @Valid ContribuitionInputRequest contribuitionInputRequest) {
        EntityCreated contributionInput = contribuitionInputService.transfer(contribuitionInputRequest);
        return new ResponseEntity<>(contributionInput, HttpStatus.OK);
    }

    @PostMapping("/reverse-contribution")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> reverse(@RequestBody @Valid ContributionReverseRequest contributionReverseRequest) {
        contribuitionInputService.reverse(contributionReverseRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    @RequestMapping("/contribution/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long transferId) {
        ContributionInput contributionInput = contribuitionInputService.findByContributionId(transferId);
        return new ResponseEntity<>(contributionInput, HttpStatus.OK);
    }

    @GetMapping()
    @RequestMapping("/contribution/{transactionCode}")
    public ResponseEntity<?> findByTransactionId(@PathVariable("transactionCode") String transactionCode) {
        ContributionInput contributionInput = contribuitionInputService.findTransactionCode(transactionCode);
        return new ResponseEntity<>(contributionInput, HttpStatus.OK);
    }

    @GetMapping("/contribution")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<ContributionInput> findAll() {
        return contribuitionInputService.findAll();
    }
}
