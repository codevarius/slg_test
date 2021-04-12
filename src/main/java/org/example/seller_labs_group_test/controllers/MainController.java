package org.example.seller_labs_group_test.controllers;

import io.swagger.annotations.*;
import org.example.seller_labs_group_test.data.dto.DailySpendingDto;
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

@Api(tags = "myapp transaction insights api")
@RestController
//@RequestMapping(value = "/myapp")
public class MainController {

    private final SpendingDataService dataService;

    @Autowired
    public MainController(SpendingDataService dataService) {
        this.dataService = dataService;
    }

    @ApiOperation(value = "get aggregated values of spending/income within specified period",
            notes = "returns list grouped by person")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SpendingDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ResponseEntity.class)
    })
    @GetMapping(value = "/spending-in-period")
    public ResponseEntity<List<SpendingDto>> spendingInPeriod(
            //Required params
            @ApiParam(value = "date starting from which transactions should be found")
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @ApiParam(value = "date ending to which transactions should be found")
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            //Optional params
            @ApiParam(value = "filter data matching category of transactions")
            @RequestParam(required = false, name = "categoryFilter") Optional<String> categoryFilterStr,
            @ApiParam(value = "filter data matching category of transactions")
            @RequestParam(required = false, name = "commentFilter") Optional<String> commentFilterStr,
            @ApiParam(value = "filter data constrained by min amount of transaction")
            @RequestParam(required = false, name = "minAmount") Optional<Long> minAmount,
            @ApiParam(value = "filter data constrained by max amount of transaction")
            @RequestParam(required = false, name = "maxAmount") Optional<Long> maxAmount
    ) {

        List<SpendingDto> payload = dataService
                .selectDataBetween(startDate, endDate)
                .filterWithIterableStrParameter(categoryFilterStr)
                .filterWithStrParameter(commentFilterStr)
                .filterByMinNumericParameter(minAmount)
                .filterByMaxNumericParameter(maxAmount)
                .getSpendingResult();

        return ResponseEntity.ok(payload);
    }

    @ApiOperation(value = "spending by days within specified period and person",
            notes = "returns list grouped by dates")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = DailySpendingDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ResponseEntity.class)
    })
    @GetMapping("/daily-spending")
    public ResponseEntity<List<DailySpendingDto>> dailySpending(
            //Required params
            @ApiParam(value = "date starting from which transactions should be found")
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @ApiParam(value = "date ending to which transactions should be found")
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @ApiParam(value = "person id 1,2...etc")
            @RequestParam(name = "person") Long personId
    ) {
        List<DailySpendingDto> payload = dataService
                .selectDataBetween(startDate, endDate)
                .filterById(Optional.ofNullable(personId))
                .getDailySpendingResult();

        return ResponseEntity.ok(payload);
    }
}
