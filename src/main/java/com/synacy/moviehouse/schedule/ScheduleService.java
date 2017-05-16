package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.exception.InvalidDataPassedException;
import com.synacy.moviehouse.exception.NoContentFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import lombok.Setter;
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

    @Autowired @Setter
    ScheduleRepository scheduleRepository;

    @Autowired @Setter
    ScheduleUtils scheduleUtils;

    @Autowired @Setter
    MovieService movieService;

    public Schedule fetchById(Long id) {
        Schedule schedule = scheduleRepository.findOne(id);

        if(schedule == null)
            throw new NoContentFoundException("Not content found");
        else
            return schedule;
    }

    public List<Schedule> fetchAll(String dateInString, Long movieId) throws ParseException {
        List<Schedule> scheduleList;

        if(dateInString != null && movieId == null) {
            Date date = scheduleUtils.dateStringParser(dateInString);
            scheduleList = scheduleRepository.findAllByDate(date);
        }
        else if(dateInString == null && movieId != null) {
            Movie movie = movieService.fetchById(movieId);
            scheduleList = scheduleRepository.findAllByMovie(movie);
        }
        else if(dateInString != null && movieId != null){
            Date date = scheduleUtils.dateStringParser(dateInString);
            Movie movie = movieService.fetchById(movieId);
            scheduleList = scheduleRepository.findAllByDateAndMovie(date,movie);
        }
        else
            scheduleList = (List)scheduleRepository.findAll();

        return scheduleList;
    }

    public Page<Schedule> fetchAllPaginated(String dateInString, Long movieId, Integer offset, Integer max) throws ParseException {
        Page<Schedule> page;

        if(dateInString != null && movieId == null) {
            Date date = scheduleUtils.dateStringParser(dateInString);
            page = scheduleRepository.findAllByDate(date, new PageRequest(offset, max));
        }
        else if(dateInString == null && movieId != null){
            Movie movie = movieService.fetchById(movieId);
            page = scheduleRepository.findAllByMovie(movie,new PageRequest(offset, max));
        }
        else if(dateInString != null && movieId != null){
            Date date = scheduleUtils.dateStringParser(dateInString);
            Movie movie = movieService.fetchById(movieId);
            page = scheduleRepository.findAllByDateAndMovie(date, movie, new PageRequest(offset, max));
        }
        else
            page = scheduleRepository.findAll(new PageRequest(offset, max));

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

        if(scheduleUtils.isValidEndDateTime(schedule.getMovie().getDuration(),startDateTime,endDateTime)
                && !scheduleUtils.isDateOverlapping(schedule))
            return  scheduleRepository.save(schedule);
        else
            throw new InvalidDataPassedException("Invalid Date Input");
    }

    public Schedule updateSchedule(Schedule schedule, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime){

        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        if(scheduleUtils.isValidEndDateTime(schedule.getMovie().getDuration(),startDateTime,endDateTime)
                && !scheduleUtils.isDateOverlapping(schedule))
            return  scheduleRepository.save(schedule);
        else
            throw new InvalidDataPassedException("Invalid Date Input");
    }

    public void deleteSchedule(Schedule schedule){
        scheduleRepository.delete(schedule);
    }


}
