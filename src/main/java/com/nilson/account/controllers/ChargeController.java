package com.nilson.account.controllers;

import com.nilson.account.models.Transaction;
import com.nilson.account.request.ChargeRequest;
import com.nilson.account.response.EntityCreated;
import com.nilson.account.request.ReverseChargeRequest;
import com.nilson.account.services.ChargeService;
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
public class ChargeController {

    @Autowired
    private ChargeService chargeService;

    @PostMapping("/charge")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody @Valid ChargeRequest chargeRequest) {
        EntityCreated transactionId = chargeService.charge(chargeRequest);
        return new ResponseEntity<>(transactionId, HttpStatus.OK);
    }

    @PostMapping("/reverse-charge")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> reverse(@RequestBody @Valid ReverseChargeRequest reverseChargeRequest) {
        chargeService.reverse(reverseChargeRequest.getTransactionId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    @RequestMapping("/charge/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long transferId) {
        Transaction transaction = chargeService.findById(transferId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/charge")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Transaction> findAll() {
        return chargeService.findAll();
    }
}
