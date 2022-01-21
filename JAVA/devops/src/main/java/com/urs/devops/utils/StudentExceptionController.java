package com.urs.devops.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionController extends RuntimeException{
    @ExceptionHandler(value = StudentNotFindException.class)
    public ResponseEntity<Object> exception (StudentNotFindException exception) {
        return new ResponseEntity<>("student not found", HttpStatus.NOT_FOUND);
    }
}
