package com.lv.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceNotFoundException {
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorMessage> runtimeException(RuntimeException e, WebRequest req) {
        return new ResponseEntity<>(ErrorMessage
            .builder()
            .timeStamp(LocalDateTime.now())
            .error(HttpStatus.BAD_REQUEST.value())
            .message("Could not create user with name " + e.getMessage())
            .path(req.getDescription(false))
            .build(), HttpStatus.BAD_REQUEST);
    }

}
