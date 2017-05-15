package com.synacy.moviehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by steven on 5/13/17.
 */

@ResponseStatus(value= HttpStatus.PARTIAL_CONTENT)
public class IncompleteInformationException extends RuntimeException{
        public IncompleteInformationException(String message) {
            super(message);
        }
}
