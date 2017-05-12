package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.schedule.Schedule;
import com.synacy.moviehouse.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule fetchById(Long id) {
        return scheduleRepository.findOne(id);
    }

    public List<Schedule> fetchAll() {
        return (List) scheduleRepository.findAll();
    }

    public Schedule createSchedule(Movie movie, Cinema cinema, Date startDateTime, Date endDateTime) {
        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime) {
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }
}
