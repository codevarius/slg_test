package org.example.seller_labs_group_test.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.seller_labs_group_test.data.entities.TransactionEntity;

import java.time.LocalDate;
import java.util.List;

public class DailySpendingDto {

    private LocalDate date;
    private Long spending;

    @JsonIgnore
    private List<TransactionEntity> transactionsList;

    public DailySpendingDto(LocalDate date, List<TransactionEntity> transactionsList) {
        this.date = date;
        this.transactionsList = transactionsList;
        setSpending(transactionsList);
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getSpending() {
        return spending;
    }

    private void setSpending(List<TransactionEntity> transactionsList) {
        this.spending = transactionsList.stream()
                .map(TransactionEntity::getAmount)
                .reduce(0L, Long::sum);
    }

    public List<TransactionEntity> getTransactionsList() {
        return transactionsList;
    }

}
