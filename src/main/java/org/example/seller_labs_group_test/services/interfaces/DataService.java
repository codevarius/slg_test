package org.example.seller_labs_group_test.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DataService<T> {

    DataService<T> selectDataBetween(LocalDate startDate, LocalDate endDate);

    DataService<T> filterWithStrParameter(Optional<String> optional);

    DataService<T> filterWithIterableStrParameter(Optional<String> optional);

    DataService<T> filterByMaxNumericParameter(Optional<Long> optional);

    DataService<T> filterByMinNumericParameter(Optional<Long> optional);

    DataService<T> filterById(Optional<Long> optional);

    List<T> getSpendingResult();

}
