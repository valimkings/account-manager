package com.nilson.account.services;

import com.nilson.account.exceptions.AccountStatusException;
import com.nilson.account.exceptions.InvalidAmountException;
import com.nilson.account.exceptions.ResourceNotFoundException;
import com.nilson.account.exceptions.AlreadyBeenReversedException;
import com.nilson.account.models.Account;
import com.nilson.account.models.Transaction;
import com.nilson.account.models.Transfer;
import com.nilson.account.repositories.AccountRepository;
import com.nilson.account.repositories.TransactionRepository;
import com.nilson.account.repositories.TransferRepository;
import com.nilson.account.request.Response.EntityCreated;
import com.nilson.account.request.TransferRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final Logger log = LogManager.getLogger(TransferService.class);

    @Transactional
    public EntityCreated transfer(TransferRequest transferRequest) {

        Account sourceAccount = accountRepository.findById(transferRequest.getSourceAccountId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Conta de origem não encontrada com id: %s", transferRequest.getSourceAccountId())));

        Account destinationAccount = accountRepository.findById(transferRequest.getDestinationAccountId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Conta destino não encontrada com id: %s", transferRequest.getSourceAccountId())));

        Transaction transaction = new Transaction(transferRequest.getAmount(), destinationAccount);
        Transfer transfer = new Transfer(sourceAccount, transaction);
        validateTransfer(transfer);

        sourceAccount.debitAmount(transferRequest.getAmount());
        destinationAccount.credit(transferRequest.getAmount());

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        log.info(format("Transferência da conta %s para a conta %s no valor de R$ %s foi feita com sucesso",
                sourceAccount.getId(), destinationAccount.getId(), transferRequest.getAmount().toString()));

        transactionRepository.save(transaction);
        transferRepository.save(transfer);

        return new EntityCreated(transfer.getId());

    }

    public void reverse(Long transferId) {

        Transfer transfer = transferRepository.findById(transferId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Transferência não encontrada com id: %s", transferId)));

        Account destinationAccount = accountRepository.findById(transfer.getTransaction().getDestinationAccount().getId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Conta não encontrada para débito com id: %s", transfer.getTransaction().getDestinationAccount().getId())));

        Account sourceAccount = accountRepository.findById(transfer.getSourceAccount().getId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Conta não encontrada para crédicot com id: %s", transfer.getTransaction().getDestinationAccount().getId())));


        validateReverse(transfer);

        sourceAccount.credit(transfer.getTransaction().getAmount());
        destinationAccount.debitAmount(transfer.getTransaction().getAmount());

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
        transfer.getTransaction().setReversed(true);
        log.info(format("Estorno da conta %s para a conta %s no valor de R$ %n foi feita com sucesso",
                destinationAccount.getId(), sourceAccount.getId(), transfer.getTransaction().getAmount()));

    }

    public Transfer findById(long transferId) {
        return transferRepository.findById(transferId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Transfer not found with id: %s", transferId)));
    }

    public Iterable<Transfer> findAll() {
        return transferRepository.findAll();
    }

    private void validateReverse(Transfer transfer) {
        accountIsActive(transfer.getSourceAccount());
        accountIsActive(transfer.getTransaction().getDestinationAccount());
        accountHasBalance(transfer.getTransaction().getDestinationAccount().getBalance(), transfer);
        transferIsReversed(transfer);
    }


    private void validateTransfer(Transfer transfer) {

        accountIsActive(transfer.getSourceAccount());
        accountIsActive(transfer.getTransaction().getDestinationAccount());
        accountHasBalance(transfer.getSourceAccount().getBalance(), transfer);

        if (transfer.getTransaction().getAmount() <= 0) {
            throw new InvalidAmountException("Valor de depósito deve ser maior que R$ 0.00 ");
        }

        if (transfer.getTransaction().getDestinationAccount().isMatrix()) {
            throw new AccountStatusException(String.format("Conta MATRIX com id: %s não pode receber depósitos, apenas aporte", transfer.getTransaction().getDestinationAccount().getId()));
        }
    }

    private void accountHasBalance(Double accountBalance, Transfer transfer) {
        if (accountBalance < transfer.getTransaction().getAmount()) {
            throw new InvalidAmountException(String.format("Conta com id: %s não tem saldo suficiente para tranferência", transfer.getSourceAccount().getId()));
        }
    }

    private void accountIsActive(Account sourceAccount) {
        if (!sourceAccount.isActive()) {
            throw new AccountStatusException(String.format("Conta com id: %s não esta ativa", sourceAccount.getId()));
        }
    }

    private void transferIsReversed(Transfer transfer) {
        if (transfer.getTransaction().isReversed()) {
            throw new AlreadyBeenReversedException("Tranferência ja foi estornada");
        }
    }

}
