package org.example.seller_labs_group_test.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.seller_labs_group_test.data.entities.PersonEntity;
import org.example.seller_labs_group_test.data.entities.TransactionEntity;

import java.util.ArrayList;
import java.util.List;

public class SpendingDto {

    @JsonIgnore
    private PersonEntity personEntity;
    @JsonIgnore
    private List<TransactionEntity> transactionsList;

    private String person;
    private Long spending;

    public SpendingDto(PersonEntity personEntity, List<TransactionEntity> transactionsList) {
        setSpending(transactionsList);
        setPerson(personEntity);
    }

    public SpendingDto(List<TransactionEntity> transactionEntities) {
        setSpending(transactionEntities);
        setPerson(transactionEntities.stream().findAny().orElseThrow().getPersonEntity());
    }

    public String getPerson() {
        return person;
    }

    private void setPerson(PersonEntity personEntity) {
        this.personEntity = personEntity;
        this.person = personEntity.getName();
    }

    public Long getSpending() {
        return spending;
    }

    public void setSpending(List<TransactionEntity> transactionsList) {
        this.transactionsList = new ArrayList<>(transactionsList);
        this.spending = transactionsList.stream()
                .map(TransactionEntity::getAmount)
                .reduce(0L, Long::sum);
    }

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public List<TransactionEntity> getTransactionsList() {
        return transactionsList;
    }
}
