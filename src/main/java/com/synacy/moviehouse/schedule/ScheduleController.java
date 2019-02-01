package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.exception.ScheduleAlreadyExistsException;
import com.synacy.moviehouse.exception.ScheduleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
