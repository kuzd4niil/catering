package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    static final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));

    @ExceptionHandler(value = CateringAlreadyExist.class)
    public ResponseEntity<Object> handleCateringAlreadyExistException(IOException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                timestamp
        );

        return new ResponseEntity<>(apiException, badRequest);
    }


















    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<Object> handleIOException(IOException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                timestamp
        );

        return new ResponseEntity<>(apiException, badRequest);
    }
}
