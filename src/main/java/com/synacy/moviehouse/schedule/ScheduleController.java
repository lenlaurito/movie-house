package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.movie.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    @RequestMapping(method = RequestMethod.GET, value = "/{scheduleId}")
    public Schedule fetchSchedule(@PathVariable(value="scheduleId") Long scheduleId) {
        return scheduleService.fetchById(scheduleId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Schedule> fetchAllSchedules(@RequestParam(value = "date", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                            @RequestParam(value = "movieId", required = false) Long movieId,
                                            @RequestParam(value = "offset", required = false) Integer offset,
                                            @RequestParam(value = "max", required = false) Integer max) {
        if (date == null && movieId == null && offset == null && max == null) {
            return scheduleService.fetchAll();
        } else {
            return scheduleService.fetchAllSchedules(date, movieId, offset, max);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule createNewSchedule(@RequestBody Schedule schedule) {

        return scheduleService.create(schedule);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{scheduleId}")
    public Schedule updateSchedule(@PathVariable(value="scheduleId") Long scheduleId,
                             @RequestBody Schedule scheduleRequest) {
        Schedule schedule = scheduleService.fetchById(scheduleId);
        return scheduleService.update(schedule, scheduleRequest.getMovie(), scheduleRequest.getCinema(),
                                                scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable(value="scheduleId") Long scheduleId) {
        Schedule schedule = scheduleService.fetchById(scheduleId);
        scheduleService.delete(schedule);
    }

}
