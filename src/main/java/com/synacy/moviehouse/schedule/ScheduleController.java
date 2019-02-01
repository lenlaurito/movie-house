package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.exception.ScheduleAlreadyExistsException;
import com.synacy.moviehouse.exception.ScheduleNotFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ScheduleController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public Schedule createNewSchedule(@RequestBody Schedule sched) throws ScheduleAlreadyExistsException {
        return scheduleService.createNewSchedule(sched);
    }

    @PutMapping("/schedule/{id}")
    public void updateSchedule(@RequestBody Schedule sched, @PathVariable long id) throws ScheduleNotFoundException {
        scheduleService.updateSchedule(sched, id);
    }

    @DeleteMapping("/schedule/{id}")
    public void deleteSchedule(@PathVariable long id) {
        scheduleService.deleteSchedule(id);
    }

    @GetMapping("/schedule/{id}")
    public Schedule getScheduleById(@PathVariable long id) {
        return scheduleService.getScheduleById(id);
    }

    @GetMapping("/schedule")
    @ResponseBody
    public List<Schedule> getSchedules(@RequestParam(required = false) long movieId, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate) {
        if ( movieId != 0 && startDate == null && endDate == null) {
            return scheduleService.getSchedulesByMovie(movieId);
        }
        else if ( movieId == 0 && startDate != null && endDate != null)
            return scheduleService.getSchedulesByDay(startDate, endDate);

        //return scheduleService.getAllSchedules();
        return null;
    }
}
