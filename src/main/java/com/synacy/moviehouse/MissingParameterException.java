package com.synacy.moviehouse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by banjoe on 5/12/17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingParameterException extends RuntimeException{
    public MissingParameterException(String message) {
        super(message);
    }
}
