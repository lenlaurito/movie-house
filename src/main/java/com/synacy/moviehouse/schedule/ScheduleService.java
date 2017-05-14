package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaRepository;
import com.synacy.moviehouse.exception.InvalidDataPassedException;
import com.synacy.moviehouse.exception.InvalidParameterException;
import com.synacy.moviehouse.exception.NoContentFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        Schedule schedule = scheduleRepository.findOne(id);

        if(schedule == null)
            throw new NoContentFoundException("Not content found");
        else
            return schedule;
    }

    public List<Schedule> fetchAll(Date date) {
        List<Schedule> scheduleList;

        if(date != null)
           scheduleList = scheduleRepository.findAllByDate(date);
        else
            scheduleList = (List)scheduleRepository.findAll();

        if(scheduleList.size() < 1)
            throw new NoContentFoundException("Not content found");
        else
            return scheduleList;
    }

    public Page<Schedule> fetchAllPaginated(Date date, Integer offset, Integer max) {
        Page<Schedule> page;

        if(date != null)
            page = scheduleRepository.findAllByDate(date,new PageRequest(offset, max));
        else
            page = scheduleRepository.findAll(new PageRequest(offset, max));

        if(page.getTotalPages() < 1)
            throw new NoContentFoundException("Not content found");
        else
            return page;
    }

    public Schedule createSchedule(Movie movie, Cinema cinema, Date startDateTime, Date endDateTime) throws ParseException {

        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        schedule.setDate(dateFormat.parse(dateFormat.format(startDateTime)));
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        if(isValidEndDateTime(schedule.getMovie().getDuration(),schedule.getStartDateTime(),schedule.getEndDateTime()))
            return  scheduleRepository.save(schedule);
        else
            throw new InvalidDataPassedException("Invalid Date Input");
    }

    public Schedule updateSchedule(Schedule schedule, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime){

        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        if(isValidEndDateTime(schedule.getMovie().getDuration(),schedule.getStartDateTime(),schedule.getEndDateTime()))
            return  scheduleRepository.save(schedule);
        else
            throw new InvalidDataPassedException("Invalid Date Input");
    }

    public void deleteSchedule(Schedule schedule){
        scheduleRepository.delete(schedule);
    }

    private Boolean isValidEndDateTime(int duration, Date startTime, Date endTime){
        int differenceInMinutes = ((int) (endTime.getTime() - startTime.getTime()))/60000;
        if(differenceInMinutes >= duration) return true;
        else return false;
    }

}
