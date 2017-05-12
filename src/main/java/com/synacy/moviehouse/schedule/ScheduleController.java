package com.synacy.moviehouse.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET, value = "/{scheduleId}")
    public Schedule fetchSchedule(@PathVariable(value="scheduleId") Long scheduleId) {
        return scheduleService.fetchById(scheduleId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Schedule> fetchAllSchedules() {
        return scheduleService.fetchAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule createNewSchedule(@RequestBody Schedule scheduleRequest) {
        return  scheduleService.createSchedule(scheduleRequest.getMovie(), scheduleRequest.getCinema(),
                scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime());
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{scheduleId}")
    public Schedule updateSchedule(@PathVariable(value="scheduleId") Long scheduleId,
                             @RequestBody Schedule scheduleRequest) {
        Schedule schedule = scheduleService.fetchById(scheduleId);
        return scheduleService.updateSchedule(schedule, scheduleRequest.getMovie(), scheduleRequest.getCinema(),
                scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable(value="scheduleId") Long scheduleId) {
        Schedule schedule = scheduleService.fetchById(scheduleId);
        scheduleService.deleteSchedule(schedule);
    }

}
