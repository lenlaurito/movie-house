package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

/**
 * Created by michael on 5/15/17.
 */
@Service
@Transactional
public class ScheduleService {

    @Autowired @Setter @Getter
    ScheduleRepository scheduleRepository;

    @Autowired @Getter
    MovieService movieService;

    @Autowired @Getter
    CinemaService cinemaService;

    public List<Schedule> fetchAllSchedules(Pageable pageable, String date) {
        Page<Schedule> schedulePage;

        if (date == null)
            schedulePage = scheduleRepository.findAll(pageable);
        else
            schedulePage = scheduleRepository.findAllByStartDateTime(date, pageable);

        return schedulePage.getContent();

    }

    public Schedule createSchedule(Date start, Date end, Movie movie, Cinema cinema) {
        Schedule schedule = new Schedule();

        schedule.setStartDateTime(start);
        schedule.setEndDateTime(end);
        schedule.setMovie(movie);
        schedule.setCinema(cinema);

        return scheduleRepository.save(schedule);
    }

    public Schedule fetchScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findOne(scheduleId);

        if (schedule == null)
            throw new NoResultException();

        return schedule;
    }

    public Schedule updateSchedule(Long scheduleId, Date start, Date end, Movie movie, Cinema cinema) {
        Schedule schedule = fetchScheduleById(scheduleId);

        schedule.setStartDateTime(start);
        schedule.setEndDateTime(end);
        schedule.setMovie(movie);
        schedule.setCinema(cinema);

        return scheduleRepository.save(schedule);
    }

    public void deleteScheduleById(Long scheduleId) {
        Schedule schedule = fetchScheduleById(scheduleId);

        scheduleRepository.delete(schedule);
    }
}
