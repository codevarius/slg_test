package org.example.seller_labs_group_test.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @JsonIgnore
    private PersonEntity personEntity;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnore
    private SpendingCategoryEntity spendingCategoryEntity;

    private String comment;

    private Long amount;

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public void setPersonEntity(PersonEntity personEntity) {
        this.personEntity = personEntity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SpendingCategoryEntity getSpendingCategoryEntity() {
        return spendingCategoryEntity;
    }

    public void setSpendingCategoryEntity(SpendingCategoryEntity spendingCategoryEntity) {
        this.spendingCategoryEntity = spendingCategoryEntity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
