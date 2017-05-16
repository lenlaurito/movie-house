package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
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

    public Schedule fetchById(Long id) {
        Schedule schedule = scheduleRepository.findOne(id);
        if (schedule == null) {
            throw new ResourceNotFoundException("Schedule with id " + id + " does not exist.");
        }
        return schedule;
    }

    public List<Schedule> fetchAll() {
        return (List) scheduleRepository.findAll();
    }

    public List<Schedule> fetchAllSchedules(Date date, Long movieId, Integer offset, Integer max) {
        if (offset != null && max != null) {
            return this.fetchPaginatedSchedules(date, movieId, offset, max).getContent();
        }
        else {
            if (date != null && movieId != null) {
                if (offset == null && max == null) {
                    return this.fetchAllByDateAndMovieId(date, movieId);
                }
                else {
                    throw new InvalidRequestException("Offset and max should both be used as parameters.");
                }
            } else {
                if (offset == null && max == null) {
                    return this.fetchAllByDateAndMovieId(date, movieId);
                }
                else {
                    throw new InvalidRequestException("Offset and max should both be used as parameters.");
                }
            }
        }
    }

    public Schedule create(Schedule schedule) {
        Schedule validatedSchedule = this.validateSchedule(schedule);
        return scheduleRepository.save(validatedSchedule);
    }

    public Schedule update(Schedule schedule, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime) {
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);
        return scheduleRepository.save(schedule);
    }

    public void delete(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    public List<Schedule> fetchAllByDateAndMovieId(Date date, Long movieId) {
        if (movieId == null) {
            return scheduleRepository.findAllByStartDateTime(date);
        }
        else if (date == null) {
            Movie movie = movieService.fetchById(movieId);
            return scheduleRepository.findAllByMovie(movie);
        }
        else {
            Movie movie = movieService.fetchById(movieId);
            return scheduleRepository.findAllByStartDateTimeAndMovie(date, movie);
        }
    }

    public Page<Schedule> fetchPaginatedSchedules(Date date, Long movieId, Integer offset, Integer max) {
        Pageable pageable = new PageRequest(offset, max, Sort.Direction.ASC, "startDateTime");
        if (date == null && movieId == null) {
            return scheduleRepository.findAll(pageable);
        } else if (date == null) {
            Movie movie = movieService.fetchById(movieId);
            return scheduleRepository.findAllByMovie(movie, pageable);
        } else if (movieId == null) {
            return scheduleRepository.findAllByStartDateTime(date, pageable);
        } else {
            Movie movie = movieService.fetchById(movieId);
            return scheduleRepository.findAllByStartDateTimeAndMovie(date, movie, pageable);
        }
    }

    protected Schedule validateSchedule(Schedule schedule) {
        Long movieId = schedule.getMovie().getId();
        Long cinemaId = schedule.getCinema().getId();
        Date startDateTime = schedule.getStartDateTime();
        Date endDateTime = schedule.getEndDateTime();
        Movie movie = movieService.fetchById(movieId);

        if (!startDateTime.before(endDateTime)) {
            throw new InvalidRequestException("Start date should be before the end date.");
        }

        if (DateUtils.onSameDate(startDateTime, endDateTime)) {
            List<Schedule> schedules = scheduleRepository.findAllByDateWithinRange(cinemaId, startDateTime, endDateTime);
            if (!schedules.isEmpty()) {
                throw new InvalidRequestException("Dates are overlapping with other schedules.");
            }

            if (movie.getDuration() > DateUtils.getDiffInMinutes(startDateTime, endDateTime)) {
                throw new InvalidRequestException("Movie duration does not fit to the schedule. Movie Duration is " + movie.getDuration());
            }
        }
        else {
            throw new InvalidRequestException("Start and end date should be on the same day.");
        }
        return schedule;
    }

}
