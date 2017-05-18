package com.synacy.moviehouse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by banjoe on 5/15/17.
 */

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistException extends RuntimeException {

    public ResourceAlreadyExistException(String message){
        super(message);
    }
}
