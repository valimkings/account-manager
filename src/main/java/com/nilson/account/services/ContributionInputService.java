package com.nilson.account.services;

import com.nilson.account.exceptions.AccountStatusException;
import com.nilson.account.exceptions.AlreadyBeenReversedException;
import com.nilson.account.exceptions.ResourceNotFoundException;
import com.nilson.account.models.Account;
import com.nilson.account.models.ContributionInput;
import com.nilson.account.repositories.AccountRepository;
import com.nilson.account.repositories.ContributionInputRepository;
import com.nilson.account.request.ContribuitionInputRequest;
import com.nilson.account.request.ContributionReverseRequest;
import com.nilson.account.response.EntityCreated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.lang.String.format;

@Service
public class ContributionInputService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContributionInputRepository contributionInputRepository;

    private static final Logger log = LogManager.getLogger(ContributionInputService.class);

    @Transactional
    public EntityCreated transfer(ContribuitionInputRequest contribuitionInputRequest) {

        Account account = accountRepository.findById(contribuitionInputRequest.getDestinationAccountId()).orElseThrow(
                () -> new ResourceNotFoundException(format("Conta MATRIX não encontrada com  id: %s", contribuitionInputRequest.getDestinationAccountId())));

        accountIsActive(account);
        accountIsMatrix(account);

        account.credit(contribuitionInputRequest.getAmount());
        accountRepository.save(account);

        log.info(format("Transferência para conta %s no valor de R$ %n foi feita com sucesso",
                contribuitionInputRequest.getDestinationAccountId(), contribuitionInputRequest.getAmount() ));

        ContributionInput contributionInput = new ContributionInput(account, UUID.randomUUID().toString(), contribuitionInputRequest.getAmount());
        contributionInputRepository.save(contributionInput);

        return new EntityCreated(contributionInput.getId());
    }

    public void reverse(ContributionReverseRequest contributionReverseRequest) {
        ContributionInput contributionInput = contributionInputRepository.findByTransactionCode(contributionReverseRequest.getTransactionCode()).orElseThrow(
                () -> new ResourceNotFoundException(format("Conta MATRIX não encontrada com transactionCode: %s", contributionReverseRequest.getTransactionCode())));


        Account account = accountRepository.findById(contributionInput.getDestinationAccount().getId()).orElseThrow(
                () -> new ResourceNotFoundException(format("Conta MATRIX não encontrada com id: %s", contributionInput.getDestinationAccount().getId())));

        accountIsMatrix(account);
        accountIsActive(account);
        contributionIsReversed(contributionInput);

        account.debitAmount(contributionInput.getAmount());
        accountRepository.save(account);

        log.info(format("Estorno para conta %s no valor de R$ %n foi feito com sucesso",
                contributionInput.getDestinationAccount(), contributionInput.getAmount() ));

        contributionInput.setReversed(true);
        contributionInputRepository.save(contributionInput);
    }

    public ContributionInput findByContributionId(long transferId) {

        return contributionInputRepository.findById(transferId).orElseThrow(
                () -> new ResourceNotFoundException(format("Tranferência não encontrda com id: %s", transferId)));
    }

    public ContributionInput findTransactionCode(String transactionCode) {
        return contributionInputRepository.findByTransactionCode(transactionCode).orElseThrow(
                () -> new ResourceNotFoundException(format("Tranferência não encontrada com transactionCode: %s", transactionCode)));
    }

    public Iterable<ContributionInput> findAll() {
        return contributionInputRepository.findAll();

    }

    private void contributionIsReversed(ContributionInput contributionInput) {
        if (contributionInput.isReversed()) {
            throw new AlreadyBeenReversedException("Estorno ja foi efetuado");
        }
    }

    private void accountIsMatrix(Account account) {
        if (!account.isMatrix()) {
            throw new AccountStatusException(format("Conta com id: %s não pode receber ou estornar aporte pois não é do tipo MATRIX", account.getId()));
        }
    }

    private void accountIsActive(Account account) {
        if (!account.isActive()) {
            throw new AccountStatusException(format("Conta com id: %s não esta ativa", account.getId()));
        }
    }

}
