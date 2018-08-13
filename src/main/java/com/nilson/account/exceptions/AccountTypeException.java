package com.nilson.account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountTypeException extends RuntimeException {

    public AccountTypeException(String message){
        super(message);
    }
}
