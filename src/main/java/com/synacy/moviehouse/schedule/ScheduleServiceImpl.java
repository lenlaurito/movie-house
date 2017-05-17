package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.exception.InvalidRequestException;
import com.synacy.moviehouse.exception.ResourceNotFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import com.synacy.moviehouse.utilities.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    public Schedule fetchScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findOne(id);
        if (schedule == null) {
            throw new ResourceNotFoundException("Schedule with id " + id + " does not exist.");
        }
        return schedule;
    }

    public List<Schedule> fetchAll() {
        return (List) scheduleRepository.findAll();
    }

    public List<Schedule> fetchAllSchedules(Date date, Long movieId) {
        if (date == null && movieId == null) {
            return (List) scheduleRepository.findAll();
        } else if (movieId == null) {
            Date begDate = DateUtils.getBegTimeOfDate(date);
            Date endDate = DateUtils.getEndTimeOfDate(date);
            return scheduleRepository.findAllByStartDateTimeBetween(begDate, endDate);
        } else if (date == null) {
            Movie movie = movieService.fetchMovieById(movieId);
            return scheduleRepository.findAllByMovie(movie);
        } else {
            Date begDate = DateUtils.getBegTimeOfDate(date);
            Date endDate = DateUtils.getEndTimeOfDate(date);
            Movie movie = movieService.fetchMovieById(movieId);
            return scheduleRepository.findAllByMovieAndStartDateTimeBetween(movie, begDate, endDate);
        }
    }

    public Page<Schedule> fetchAllSchedulesWithPagination(Date date, Long movieId, Integer offset, Integer max) {
        Pageable pageable = new PageRequest(offset, max, Sort.Direction.ASC, "startDateTime");
        if (date == null && movieId == null) {
            return scheduleRepository.findAll(pageable);
        } else if (movieId == null) {
            Date begDate = DateUtils.getBegTimeOfDate(date);
            Date endDate = DateUtils.getEndTimeOfDate(date);
            return scheduleRepository.findAllByStartDateTimeBetween(begDate, endDate, pageable);
        } else if (date == null) {
            Movie movie = movieService.fetchMovieById(movieId);
            return scheduleRepository.findAllByMovie(movie, pageable);
        } else {
            Date begDate = DateUtils.getBegTimeOfDate(date);
            Date endDate = DateUtils.getEndTimeOfDate(date);
            Movie movie = movieService.fetchMovieById(movieId);
            return scheduleRepository.findAllByMovieAndStartDateTimeBetween(movie, begDate, endDate, pageable);
        }
    }

    public Schedule createSchedule(Movie movieToCreate, Cinema cinemaToCreate, Date startDateTime, Date endDateTime) {
        Movie movie = movieService.fetchMovieById(movieToCreate.getId());
        Cinema cinema = cinemaService.fetchCinemaById(cinemaToCreate.getId());

        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        this.validateSchedule(cinema, movie, startDateTime, endDateTime);
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

    protected boolean validateSchedule(Cinema cinema, Movie movie, Date startDateTime, Date endDateTime) {
        if (!startDateTime.before(endDateTime)) {
            throw new InvalidRequestException("Start date should be before the end date.");
        }

        if (DateUtils.onSameDate(startDateTime, endDateTime)) {
            List<Schedule> schedules = scheduleRepository.findAllByCinemaAndStartDateTimeLessThanAndEndDateTimeGreaterThanEqual(cinema, startDateTime, endDateTime);
            if (!schedules.isEmpty()) {
                throw new InvalidRequestException("Dates are overlapping with other schedules.");
            }
            if (movie.getDuration() > DateUtils.getDiffInMinutes(startDateTime, endDateTime)) {
                throw new InvalidRequestException("Movie duration does not fit to the schedule. Movie Duration is " + movie.getDuration());
            }
        } else {
            throw new InvalidRequestException("Start and end date should be on the same day.");
        }
        return true;
    }

}
