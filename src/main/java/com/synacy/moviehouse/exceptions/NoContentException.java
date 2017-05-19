package com.synacy.moviehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by kenichigouang on 5/15/17.
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoContentException extends RuntimeException{
    public NoContentException(String message) {
        super(message);
    }
}
