package com.synacy.moviehouse.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, ClassCastException.class })
    protected ResponseEntity<Object> handleIllegalArguments(RuntimeException ex, WebRequest request) {
        Map<String, Object> response = getDefaultResponse(ex, request, HttpStatus.BAD_REQUEST);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleMissingResource(RuntimeException ex, WebRequest request) {
        Map<String, Object> response = getDefaultResponse(ex, request, HttpStatus.NOT_FOUND);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    protected ResponseEntity<Object> handleDatabaseConstraints(DataIntegrityViolationException ex, WebRequest request) {
        String message = "Could not update/delete a record if tagged by a schedule.";
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", new Date());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", HttpStatus.CONFLICT.getReasonPhrase());
        response.put("exception", ex.getClass());
        response.put("message", message);
        response.put("path", request.getDescription(false).split("=")[1]);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, response, headers, HttpStatus.CONFLICT, request);
    }

    private <T extends Exception> Map<String, Object> getDefaultResponse(T ex, WebRequest request, HttpStatus status) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", new Date());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("exception", ex.getClass());
        response.put("message", ex.getMessage());
        response.put("path", request.getDescription(false).split("=")[1]);
        return response;
    }

}
