package com.nilson.account.repositories;


import com.nilson.account.models.ContributionInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ContributionInputRepository extends JpaRepository<ContributionInput, Long> {

    Optional<ContributionInput> findByTransactionCode(String transactionCode);

}
