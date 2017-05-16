package com.synacy.moviehouse.exceptions;

/**
 * Created by kenichigouang on 5/16/17.
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message){
        super(message);
    }
}
