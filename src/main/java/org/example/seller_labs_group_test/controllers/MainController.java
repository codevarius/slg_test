package org.example.seller_labs_group_test.controllers;

import org.example.seller_labs_group_test.data.dto.SpendingDto;
import org.example.seller_labs_group_test.services.SpendingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/myapp")
public class MainController {

    private final SpendingDataService dataService;

    @Autowired
    public MainController(SpendingDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping(value = "/spending-in-period")
    public ResponseEntity<List<SpendingDto>> spendingInPeriod(
            //Required params
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            //Optional params
            @RequestParam(required = false, name = "categoryFilter") Optional<String> categoryFilterStr,
            @RequestParam(required = false, name = "commentFilter") Optional<String> commentFilterStr,
            @RequestParam(required = false, name = "minAmount") Optional<Long> minAmount,
            @RequestParam(required = false, name = "maxAmount") Optional<Long> maxAmount
    ) {

        List<SpendingDto> payload = dataService
                .selectDataBetween(startDate, endDate)
                .filterWithIterableStrParameter(categoryFilterStr)
                .filterWithStrParameter(commentFilterStr)
                .filterByMinNumericParameter(minAmount)
                .filterByMaxNumericParameter(maxAmount)
                .getResult();

        return ResponseEntity.ok(payload);
    }
}
