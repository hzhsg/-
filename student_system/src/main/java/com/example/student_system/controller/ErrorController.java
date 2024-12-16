package com.example.student_system.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorController {
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public String error(ValidationException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public String runTimeError(RuntimeException e) {
        return e.getMessage();
    }
}
