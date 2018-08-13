package com.nilson.account.services;

import com.nilson.account.exceptions.AccountStatusException;
import com.nilson.account.exceptions.AlreadyBeenReversedException;
import com.nilson.account.exceptions.InvalidAmountException;
import com.nilson.account.exceptions.ResourceNotFoundException;
import com.nilson.account.models.Account;
import com.nilson.account.models.Transaction;
import com.nilson.account.models.Transfer;
import com.nilson.account.repositories.AccountRepository;
import com.nilson.account.repositories.TransactionRepository;
import com.nilson.account.request.ChargeRequest;
import com.nilson.account.request.Response.EntityCreated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
public class ChargeService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final Logger log = LogManager.getLogger(ChargeService.class);

    @Transactional
    public EntityCreated charge(ChargeRequest chargeRequest) {


        Account destinationAccount = accountRepository.findById(chargeRequest.getDestinationAccountId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Conta destino não encontrada com id: %s", chargeRequest.getDestinationAccountId())));

        Transaction transaction = new Transaction(chargeRequest.getAmount(), destinationAccount);
        validateTransaction(transaction);

        destinationAccount.credit(chargeRequest.getAmount());

        accountRepository.save(destinationAccount);
        log.info(format("Recarga para conta %s foi feita com sucesso", destinationAccount.getId()));

        transactionRepository.save(transaction);

        return new EntityCreated(transaction.getId());

    }

    public void reverse(Long transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Transação não encontrada  com id: %s", transactionId)));

        Account account = accountRepository.findById(transaction.getDestinationAccount().getId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Conta não encontrada com id: %s", transaction.getDestinationAccount().getId())));

        validateReverse(transaction, account);
        account.debitAmount(transaction.getAmount());
        accountRepository.save(account);
        transaction.setReversed(true);

        transactionRepository.save(transaction);
        log.info(format("Estorno da carga foi feita com sucesso para conta %s", account.getId() ));

    }

    public Transaction findById(long transferId) {
        return transactionRepository.findById(transferId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Transfer not found with id: %s", transferId)));
    }

    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    private void validateReverse(Transaction transaction, Account account) {
        accountIsActive(account);
        accountHasBalance(account.getBalance(), transaction);
        transactionferIsReversed(transaction);
    }


    private void validateTransaction(Transaction transaction) {

        accountIsActive(transaction.getDestinationAccount());

        if (transaction.getAmount() <= 0) {
            throw new InvalidAmountException("Valor de depósito deve ser maior que R$ 0.00 ");
        }

        if (transaction.getDestinationAccount().isMatrix()) {
            throw new AccountStatusException(String.format("Conta MATRIX com id: %s não pode receber depósitos, apenas aporte",
                    transaction.getDestinationAccount().getId()));
        }
    }

    private void accountHasBalance(Double accountBalance, Transaction transaction) {
        if (accountBalance < transaction.getAmount()) {
            throw new InvalidAmountException(String.format("Conta com id: %s não tem saldo suficiente para estorno", transaction.getDestinationAccount().getId()));
        }
    }

    private void accountIsActive(Account sourceAccount) {
        if (!sourceAccount.isActive()) {
            throw new AccountStatusException(String.format("Conta com id: %s não esta ativa", sourceAccount.getId()));
        }
    }

    private void transactionferIsReversed(Transaction transaction) {
        if (transaction.isReversed()) {
            throw new AlreadyBeenReversedException("Carga ja foi estornada");
        }
    }

}
