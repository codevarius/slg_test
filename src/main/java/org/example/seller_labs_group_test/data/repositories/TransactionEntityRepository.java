package org.example.seller_labs_group_test.data.repositories;

import org.example.seller_labs_group_test.data.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findTransactionEntityByDateBetween(LocalDate startDate, LocalDate endDate);
}
