package com.nilson.account.controllers;

import com.nilson.account.models.Transfer;
import com.nilson.account.response.EntityCreated;
import com.nilson.account.request.ReverseRequest;
import com.nilson.account.request.TransferRequest;
import com.nilson.account.services.TransferService;
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
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody @Valid TransferRequest transferRequest) {
        EntityCreated transferId = transferService.transfer(transferRequest);
        return new ResponseEntity<>(transferId, HttpStatus.OK);
    }

    @PostMapping("/reverse")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> reverse(@RequestBody @Valid ReverseRequest reverseRequest) {
        transferService.reverse(reverseRequest.getTransferId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    @RequestMapping("/transfer/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long transferId) {
        Transfer transfer = transferService.findById(transferId);
        return new ResponseEntity<>(transfer, HttpStatus.OK);
    }

    @GetMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Transfer> findAll() {
        return transferService.findAll();
    }
}
