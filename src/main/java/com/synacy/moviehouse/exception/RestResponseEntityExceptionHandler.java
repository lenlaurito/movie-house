package com.synacy.moviehouse.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("exception", ex.getClass());
        response.put("message", ex.getMessage());
        response.put("status", Integer.toString(HttpStatus.BAD_REQUEST.value()));
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("path", request.getDescription(false));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleMissingResource(RuntimeException ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("exception", ex.getClass());
        response.put("message", ex.getMessage());
        response.put("status", Integer.toString(HttpStatus.NOT_FOUND.value()));
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("path", request.getDescription(false));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
