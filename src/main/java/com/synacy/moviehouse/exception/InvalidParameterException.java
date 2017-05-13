package com.synacy.moviehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by steven on 5/12/17.
 */
@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
public class InvalidParameterException extends RuntimeException{
    public InvalidParameterException(String message) {
        super(message);
    }
}
