package org.example.seller_labs_group_test.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "spending_category")
public class SpendingCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "spendingCategoryEntity")
    @JsonIgnore
    private List<TransactionEntity> transactionList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TransactionEntity> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<TransactionEntity> transactionList) {
        this.transactionList = transactionList;
    }
}
