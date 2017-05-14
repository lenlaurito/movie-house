package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.exception.IncompleteInformationException;
import com.synacy.moviehouse.exception.InvalidParameterException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by steven on 5/12/17.
 */
@RestController
@RequestMapping(value = "/api/v1/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    MovieService movieService;

    @Autowired
    CinemaService cinemaService;

    @RequestMapping(method = RequestMethod.GET, value = "/{scheduleId}")
    public Schedule fetchSchedule(@PathVariable(value = "scheduleId") Long scheduleID){
        return scheduleService.fetchById(scheduleID);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity fetchAllSchedule(@RequestParam(value = "offset", required = false) Integer offset,
                                           @RequestParam(value = "max", required = false) Integer max,
                                           @RequestParam(value = "date", required = false) String dateInString) throws ParseException {

        if (offset == null && max == null) {
            List<Schedule> scheduleList =  scheduleService.fetchAll(dateInString);
            return ResponseEntity.ok().body(scheduleList);
        }
        else if(offset != null && max != null) {
            Page<Schedule> schedulePage =  scheduleService.fetchAllPaginated(dateInString, offset, max);
            return ResponseEntity.ok().body(schedulePage);
        }
        else{
            throw new InvalidParameterException("Parameters incomplete or isn't acceptable");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule createSchedule(@RequestBody Schedule schedule) throws ParseException {

        Movie movie = movieService.fetchById(schedule.getMovie().getId());
        Cinema cinema = cinemaService.fetchById(schedule.getCinema().getId());

        if(movie == null || cinema == null || schedule.getStartDateTime() == null || schedule.getEndDateTime() == null)
            throw new IncompleteInformationException("Missing some required information");
        else
            return  scheduleService.createSchedule(movie,cinema,schedule.getStartDateTime(),schedule.getEndDateTime());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{scheduleId}")
    public Schedule updateSchedule(@PathVariable(value = "scheduleId") Long scheduleId, @RequestBody Schedule schedule) {
        Movie movie = movieService.fetchById(schedule.getMovie().getId());
        Cinema cinema = cinemaService.fetchById(schedule.getCinema().getId());
        Schedule scheduleToBeUpdated = scheduleService.fetchById(scheduleId);

        if(movie == null || cinema == null || schedule.getStartDateTime() == null || schedule.getEndDateTime() == null)
            throw new IncompleteInformationException("Missing some required information");
        else
            return scheduleService.updateSchedule(scheduleToBeUpdated, movie, cinema, schedule.getStartDateTime(), schedule.getEndDateTime());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable(value = "scheduleId") Long scheduleId){
        Schedule schedule = scheduleService.fetchById(scheduleId);
        scheduleService.deleteSchedule(schedule);
    }

}
