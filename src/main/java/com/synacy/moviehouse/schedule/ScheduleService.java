package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaRepository;
import com.synacy.moviehouse.exception.InvalidDataPassedException;
import com.synacy.moviehouse.exception.InvalidParameterException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by steven on 5/12/17.
 */
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

        if(isValidEndDateTime(schedule.getMovie().getDuration(),schedule.getStartDateTime(),schedule.getEndDateTime()))
            return  scheduleRepository.save(schedule);
        else
            throw new InvalidDataPassedException("Invalid Dates for the schedule");

    }

    public Schedule updateSchedule(Schedule schedule, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime){

        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        if(isValidEndDateTime(schedule.getMovie().getDuration(),schedule.getStartDateTime(),schedule.getEndDateTime()))
            return  scheduleRepository.save(schedule);
        else
            throw new InvalidDataPassedException("Invalid Dates for the schedule");
    }

    public void deleteSchedule(Schedule schedule){
        scheduleRepository.delete(schedule);
    }

    public Boolean isValidEndDateTime(int duration, Date startTime, Date endTime){
        int differenceInMinutes = ((int) (endTime.getTime() - startTime.getTime()))/60000;
        if(differenceInMinutes >= duration) return true;
        else return false;
    }

}
