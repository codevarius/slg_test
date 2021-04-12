package org.example.seller_labs_group_test.controllers;

import org.example.seller_labs_group_test.enums.MyAppDelimeters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    protected ResponseEntity<Object> handleConflict(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body(
                String.join(MyAppDelimeters.SPACE.value, "🤔 We have some problems:", ex.getMessage()));
    }

    //TODO other exceptions
}
