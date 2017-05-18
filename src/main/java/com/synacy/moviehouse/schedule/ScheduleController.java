package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.exception.IncompleteInformationException;
import com.synacy.moviehouse.exception.InvalidParameterException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * Created by steven on 5/12/17.
 */
@RestController
@RequestMapping(value = "/api/v1/schedule")
public class ScheduleController {

    @Autowired @Setter
    ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET, value = "/{scheduleId}")
    public Schedule fetchSchedule(@PathVariable(value = "scheduleId") Long scheduleId){
        return scheduleService.fetchById(scheduleId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity fetchAllSchedule(@RequestParam(value = "offset", required = false) Integer offset,
                                           @RequestParam(value = "max", required = false) Integer max,
                                           @RequestParam(value = "date", required = false) String dateInString,
                                           @RequestParam(value = "movieId", required = false) Long movieId) throws ParseException {

        ResponseEntity responseEntity = null;

        if (offset == null && max == null)
            responseEntity = ResponseEntity.ok().body(scheduleService.fetchAll(dateInString, movieId));
        else if(offset != null && max != null)
            responseEntity = ResponseEntity.ok().body(scheduleService.fetchAllPaginated(dateInString, movieId, offset, max));
        else if((offset != null && max == null) || (offset == null && max != null))
            throw new InvalidParameterException("Parameters incomplete or isn't acceptable");

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule createSchedule(@RequestBody Schedule schedule) throws ParseException {
        if(schedule.getMovie() == null || schedule.getCinema() == null || schedule.getStartDateTime() == null || schedule.getEndDateTime() == null)
            throw new IncompleteInformationException("Missing some required information");

        return scheduleService.createSchedule(schedule.getMovie().getId(), schedule.getCinema().getId(), schedule.getStartDateTime(), schedule.getEndDateTime());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{scheduleId}")
    public Schedule updateSchedule(@PathVariable(value = "scheduleId") Long scheduleId, @RequestBody Schedule schedule) throws ParseException {
        if(schedule.getMovie() == null || schedule.getCinema() == null || schedule.getStartDateTime() == null || schedule.getEndDateTime() == null)
            throw new IncompleteInformationException("Missing some required information");

        return scheduleService.updateSchedule(scheduleId, schedule.getMovie().getId(), schedule.getCinema().getId(), schedule.getStartDateTime(), schedule.getEndDateTime());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable(value = "scheduleId") Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);
    }

}
