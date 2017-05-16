package com.synacy.moviehouse.schedule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by michael on 5/16/17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScheduleNotAvailableException extends RuntimeException {
    public ScheduleNotAvailableException(String message) {
        super(message);
    }
}
