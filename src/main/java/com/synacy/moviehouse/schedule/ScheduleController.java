package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.MissingParameterException;
import com.synacy.moviehouse.ResourceNotFoundException;
import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by banjoe on 5/15/17.
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
    public Schedule fetchScheduleById(@PathVariable Long scheduleId){

        Schedule schedule = scheduleService.fetchScheduleById(scheduleId);

        if(schedule == null) {
            throw new ResourceNotFoundException("Schedule does not exist");
        }

        return schedule;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity fetchAllSchedule(@RequestParam(value = "offset", required = false) Integer offset,
                                        @RequestParam(value = "max", required = false) Integer max,
                                        @RequestParam(value = "date", required = false) String date,
                                        @RequestParam(value = "movie", required = false) Long movieId) {

        if (offset == null && max == null) {
            List<Schedule> allSchedule = scheduleService.fetchAllSchedule(date, movieId);

            if(allSchedule.isEmpty()){
                throw new ResourceNotFoundException("No available schedule found.");
            }

            return ResponseEntity.ok().body(allSchedule);
        }
        else if(offset != null && max != null) {
            Page<Schedule> paginatedSchedule = scheduleService.fetchPaginatedSchedule(date, movieId, offset, max);

            if(paginatedSchedule.getContent().isEmpty()) {
                throw new ResourceNotFoundException("No available schedule found.");
            }

            return ResponseEntity.ok().body(paginatedSchedule);
        } else {
            throw new MissingParameterException("Need both offset and max.");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule createSchedule(@RequestBody Schedule schedule) {

        Movie movie = movieService.fetchMovieById(schedule.getMovie().getId());
        Cinema cinema = cinemaService.fetchCinemaById(schedule.getCinema().getId());

        if(movie == null)
            throw new ResourceNotFoundException("Movie does not exist.");
        if(cinema == null)
            throw new ResourceNotFoundException("Cinema does not exist.");

        return scheduleService.saveNewSchedule(movie, cinema, schedule.getStartDateTime(), schedule.getEndDateTime());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{scheduleId}")
    public Schedule updateSchedule(@PathVariable Long scheduleId, @RequestBody Schedule scheduleRequest) {
        Schedule schedule = scheduleService.fetchScheduleById(scheduleId);

        if(schedule == null) {
            throw new ResourceNotFoundException("Schedule does not exist");
        }

        return scheduleService.updateSchedule(schedule, scheduleRequest.getMovie(), scheduleRequest.getCinema(), scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable(value="scheduleId") Long scheduleId) {
        Schedule schedule = scheduleService.fetchScheduleById(scheduleId);

        if (schedule == null) {
            throw new ResourceNotFoundException("Schedule does not exist");
        }

        scheduleService.deleteSchedule(schedule);
    }

    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void setCinemaService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

}
