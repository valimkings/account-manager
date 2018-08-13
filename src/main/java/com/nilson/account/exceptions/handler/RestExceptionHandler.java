package com.nilson.account.exceptions.handler;

import com.nilson.account.exceptions.AccountStatusException;
import com.nilson.account.exceptions.AccountTypeException;
import com.nilson.account.exceptions.ExceptionBuild;
import com.nilson.account.exceptions.InvalidAmountException;
import com.nilson.account.exceptions.ResourceNotFoundException;
import com.nilson.account.exceptions.AlreadyBeenReversedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ExceptionBuild build = ExceptionBuild.Builder.newBuilder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(resourceNotFoundException.getMessage())
                .build();

        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountTypeException.class)
    public ResponseEntity<?> handlerInvalidAccountTypeException(AccountTypeException accountTypeException) {
        ExceptionBuild build = ExceptionBuild.Builder.newBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(accountTypeException.getMessage())
                .build();

        return new ResponseEntity<>(build, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<?> handlerInvalidAmountException(InvalidAmountException invalidAmountException) {
        ExceptionBuild build = ExceptionBuild.Builder.newBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(invalidAmountException.getMessage())
                .build();

        return new ResponseEntity<>(build, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<?> handlerAccountStatusException(AccountStatusException accountStatusException) {
        ExceptionBuild build = ExceptionBuild.Builder.newBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(accountStatusException.getMessage())
                .build();

        return new ResponseEntity<>(build, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyBeenReversedException.class)
    public ResponseEntity<?> handlerTransferAlreadyReversedException(AlreadyBeenReversedException transferAlreadyReversedFoundException) {
        ExceptionBuild build = ExceptionBuild.Builder.newBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(transferAlreadyReversedFoundException.getMessage())
                .build();

        return new ResponseEntity<>(build, HttpStatus.BAD_REQUEST);
    }
}
