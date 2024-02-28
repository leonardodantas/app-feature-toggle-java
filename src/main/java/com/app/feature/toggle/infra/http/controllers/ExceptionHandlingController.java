package com.app.feature.toggle.infra.http.controllers;


import com.app.feature.toggle.app.exceptions.ConfigNotFoundException;
import com.app.feature.toggle.infra.http.jsons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(ConfigNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(final ConfigNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(exception.getMessage()));
    }
}
