package com.imaginnovate.employeetax.exception;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeTaxExceptionHandler {
    @ExceptionHandler(value = {EmployeeTaxException.class})
    public ResponseEntity<Object> handleCouponNotFoundException(EmployeeTaxException e) {
        return new ResponseEntity<>(e.getMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException exception) {

        HashMap<String, String> map = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            map.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(map, org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

        return new ResponseEntity<>(exception.getLocalizedMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
    }
}
