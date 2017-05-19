package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.exceptions.InvalidMovieTimeSlotException;
import com.synacy.moviehouse.exceptions.MissingRequiredFieldsException;
import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */

@RestController
@RequestMapping(value = "api/v1/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    MovieService movieService;

    @Autowired
    CinemaService cinemaService;

    @GetMapping
    public ResponseEntity fetchAllSchedules(@RequestParam(value = "date", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") String date,
                                            @RequestParam(value = "offset", required = false) Integer offset,
                                            @RequestParam(value = "max", required = false) Integer max,
                                            @RequestParam(value = "movie", required = false) Long movieId) {
        if (offset == null && max == null) {
            List<Schedule> schedules =  scheduleService.fetchAll(date, movieId);
            return ResponseEntity.ok().body(schedules);
        }
        else if(offset != null && max != null) {
            Page<Schedule> schedules =  scheduleService.fetchAllPaginated(date, movieId, offset, max);
            return ResponseEntity.ok().body(schedules);
        }
        else{
            throw new InvalidParameterException("Parameters are incomplete or unacceptable.");
        }
    }

    @GetMapping("/{scheduleId}")
    public Schedule fetchSchedule(@PathVariable(value = "scheduleId") Long scheduleId) {
        return scheduleService.fetchById(scheduleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule createSchedule(@RequestBody Schedule scheduleRequest) {
        Movie movie = movieService.fetchById(scheduleRequest.getMovie().getId());
        Cinema cinema = cinemaService.fetchById(scheduleRequest.getCinema().getId());

        if(movie == null || cinema == null || scheduleRequest.getStartDateTime() == null || scheduleRequest.getEndDateTime() == null) {
            throw new MissingRequiredFieldsException("Some required data is missing.");
        } else {
            if(scheduleService.scheduleConflicts(cinema, scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime())) {
                throw new InvalidMovieTimeSlotException("This schedule is in conflict with an existing one.");
            } else {
                return scheduleService.createSchedule(movie, cinema, scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime());
            }
        }
    }

    @PutMapping("/{scheduleId}")
    public Schedule updateSchedule(@PathVariable(value = "scheduleId") Long scheduleId,
                                   @RequestBody Schedule scheduleRequest) {
        Movie movie = movieService.fetchById(scheduleRequest.getMovie().getId());
        Cinema cinema = cinemaService.fetchById(scheduleRequest.getCinema().getId());
        Schedule schedule = scheduleService.fetchById(scheduleId);

        if(movie == null || cinema == null || scheduleRequest.getStartDateTime() == null || scheduleRequest.getEndDateTime() == null) {
            throw new MissingRequiredFieldsException("Some required data is missing.");
        } else {
            if(scheduleService.scheduleConflicts(cinema, scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime())) {
                throw new InvalidMovieTimeSlotException("This schedule is in conflict with an existing one.");
            } else {
                return scheduleService.updateSchedule(schedule, movie, cinema, scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime());
            }
        }
    }

    @DeleteMapping("/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable(value = "scheduleId") Long scheduleId) {
        Schedule schedule = scheduleService.fetchById(scheduleId);
        scheduleService.deleteSchedule(schedule);
    }
}
