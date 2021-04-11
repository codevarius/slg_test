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
    private Long amount;

    public SpendingDto(PersonEntity personEntity, List<TransactionEntity> transactionsList) {
        setAmount(transactionsList);
        setPerson(personEntity);
    }

    public SpendingDto(List<TransactionEntity> transactionEntities) {
        setAmount(transactionEntities);
        setPerson(transactionEntities.stream().findAny().orElseThrow().getPersonEntity());
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(PersonEntity personEntity) {
        this.personEntity = personEntity;
        this.person = personEntity.getName();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(List<TransactionEntity> transactionsList) {
        this.transactionsList = new ArrayList<>(transactionsList);
        this.amount = transactionsList.stream()
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
