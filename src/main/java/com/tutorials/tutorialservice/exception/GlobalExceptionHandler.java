package com.tutorials.tutorialservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.Collections;
import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RunTimeExceptionPlaceHolder.class)
    public ResponseEntity<Object> handleException(RunTimeExceptionPlaceHolder exceptionPlaceHolder) {
      CustomExceptionResponse exceptionResponse = populateException("400", exceptionPlaceHolder.getMessage());
      log.error("Something went wrong, Exception " +exceptionPlaceHolder.getMessage());
      exceptionPlaceHolder.printStackTrace();
      return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        CustomExceptionResponse response = populateException("500", e.getMessage());
        log.error("Exception Occurred "+e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleException(AccessDeniedException exception) {
        CustomExceptionResponse response = populateException("401", exception.getMessage());
        log.error("Exception Occurred "+ exception.getMessage());
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public CustomExceptionResponse populateException(String code, String message) {
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse();
        exceptionResponse.setUuid(UUID.randomUUID());

        CustomException exception = new CustomException();
        exception.setCode(code);
        exception.setMessage(message);

        exceptionResponse.setExceptionList(Collections.singletonList(exception));
        return exceptionResponse;
    }
}
