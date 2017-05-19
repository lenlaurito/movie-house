package com.synacy.moviehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by kenichigouang on 5/15/17.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MissingRequiredFieldsException extends RuntimeException{
    public MissingRequiredFieldsException(String message){
        super(message);
    }
}
