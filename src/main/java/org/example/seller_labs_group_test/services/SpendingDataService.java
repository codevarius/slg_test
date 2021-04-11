package org.example.seller_labs_group_test.services;

import org.example.seller_labs_group_test.data.dto.SpendingDto;
import org.example.seller_labs_group_test.data.entities.PersonEntity;
import org.example.seller_labs_group_test.data.entities.TransactionEntity;
import org.example.seller_labs_group_test.data.repositories.TransactionEntityRepository;
import org.example.seller_labs_group_test.services.interfaces.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class SpendingDataService implements DataService<SpendingDto> {

    private final TransactionEntityRepository repository;
    private List<SpendingDto> list;

    @Autowired
    public SpendingDataService(TransactionEntityRepository repository) {
        this.repository = repository;
        this.list = new ArrayList<>();
    }

    @Override
    public DataService<SpendingDto> selectDataBetween(LocalDate startDate, LocalDate endDate) {
        if (!this.list.isEmpty()) {
            this.list = this.list.stream()
                    .map(SpendingDto::getTransactionsList)
                    .map(transactionEntities -> transactionEntities.stream()
                            .filter(transactionEntity -> transactionEntity.getDate().isAfter(startDate)
                                    && transactionEntity.getDate().isBefore(endDate))
                            .collect(Collectors.toList()))
                    .map(SpendingDto::new)
                    .collect(Collectors.toList());

        } else {
            Map<PersonEntity, List<TransactionEntity>> groupByPerson = repository
                    .findTransactionEntityByDateBetween(startDate, endDate).stream()
                    .collect(Collectors.groupingBy(TransactionEntity::getPersonEntity));

            for (Map.Entry<PersonEntity, List<TransactionEntity>> transactionsGroup : groupByPerson.entrySet()) {
                this.list.add(new SpendingDto(transactionsGroup.getKey(), transactionsGroup.getValue()));
            }
        }
        return this;
    }

    @Override
    public DataService<SpendingDto> filterWithStrParameter(Optional<String> optional) {
        if (optional.isPresent()) {
            var listOfLists = this.list.stream()
                    .map(SpendingDto::getTransactionsList)
                    .flatMap(List::stream).collect(Collectors.toList()).stream()
                    .filter(transactionEntity -> transactionEntity.getSpendingCategoryEntity().getName().contains(optional.get())
                            || transactionEntity.getComment().contains(optional.get())).collect(Collectors.toList());

            Map<PersonEntity, List<TransactionEntity>> regroupedTransactionsMap = listOfLists.stream()
                    .collect(Collectors.groupingBy(TransactionEntity::getPersonEntity));
            this.list.clear();
            for (Map.Entry<PersonEntity, List<TransactionEntity>> transactionsGroup : regroupedTransactionsMap.entrySet()) {
                this.list.add(new SpendingDto(transactionsGroup.getKey(), transactionsGroup.getValue()));
            }
        }
        return this;
    }

    @Override
    public DataService<SpendingDto> filterWithIterableStrParameter(Optional<String> optional) {
        if (optional.isPresent()) {
            String[] parameterList = optional.get()
                    .toLowerCase(Locale.ROOT)
                    .replaceAll("[\"'\\[\\]{}]", "")
                    .split(",");

            for (String parameter : parameterList) {
                filterWithStrParameter(Optional.of(parameter));
            }
        }

        return this;
    }

    @Override
    public DataService<SpendingDto> filterByMaxNumericParameter(Optional<Long> optional) {
        this.list = this.list.stream()
                .filter(spendingDto -> spendingDto.getAmount() < optional.orElse(Long.MAX_VALUE))
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public DataService<SpendingDto> filterByMinNumericParameter(Optional<Long> optional) {
        this.list = this.list.stream()
                .filter(spendingDto -> spendingDto.getAmount() > optional.orElse(Long.MIN_VALUE))
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public List<SpendingDto> getResult() {
        List<SpendingDto> resultList = new ArrayList<>(this.list);
        this.list.clear();
        return resultList;
    }
}
