package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/{scheduleId}")
    public ResponseEntity fetchSchedule(@PathVariable(value="scheduleId") Long scheduleId) {
        Schedule schedule =  scheduleService.fetchScheduleById(scheduleId);
        return ResponseEntity.ok().body(schedule);
    }

    @GetMapping
    public ResponseEntity fetchAllSchedules(@RequestParam(value = "date", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                            @RequestParam(value = "movieId", required = false) Long movieId,
                                            @RequestParam(value = "offset", required = false) Integer offset,
                                            @RequestParam(value = "max", required = false) Integer max) {
        if (offset == null && max == null) {
            List<Schedule> schedules = scheduleService.fetchAllSchedules(date, movieId);
            return ResponseEntity.ok().body(schedules);
        } else if (offset != null && max != null) {
            Page<Schedule> schedules = scheduleService.fetchAllSchedulesWithPagination(date, movieId, offset, max);
            return ResponseEntity.ok().body(schedules);
        } else {
            throw new InvalidRequestException("Offset and max should both be used as parameters.");
        }
    }

    @PostMapping
    public ResponseEntity createNewSchedule(@RequestBody Schedule scheduleRequest) {
        Schedule schedule = scheduleService.createSchedule(scheduleRequest.getMovie(), scheduleRequest.getCinema(),
                scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime());
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity updateSchedule(@PathVariable(value="scheduleId") Long scheduleId,
                                   @RequestBody Schedule scheduleRequest) {
        Schedule scheduleToUpdate = scheduleService.fetchScheduleById(scheduleId);
        Schedule schedule = scheduleService.updateSchedule(scheduleToUpdate, scheduleRequest.getMovie(), scheduleRequest.getCinema(),
                scheduleRequest.getStartDateTime(), scheduleRequest.getEndDateTime());
        return ResponseEntity.ok().body(schedule);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity deleteSchedule(@PathVariable(value="scheduleId") Long scheduleId) {
        Schedule schedule = scheduleService.fetchScheduleById(scheduleId);
        scheduleService.deleteSchedule(schedule);
        return ResponseEntity.noContent().build();
    }

}
