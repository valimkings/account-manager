package com.nilson.account.repositories;


import com.nilson.account.models.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TransferRepository extends CrudRepository<Transfer, Long> {
}
