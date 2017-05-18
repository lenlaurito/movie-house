package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.ResourceAlreadyExistException;
import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by banjoe on 5/15/17.
 */

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    ScheduleRepo scheduleRepo;

    private Date startDate = null;
    private Date endDate = null;

    @Override
    public Schedule fetchScheduleById(Long id) {
        return scheduleRepo.findOne(id);
    }

    @Override
    public Schedule saveNewSchedule(Movie movie, Cinema cinema, Date startDateTime, Date endDateTime) {

        List<Schedule> resultList = scheduleRepo.findAllByStartDateTimeBetweenAndCinemaId(startDateTime, endDateTime, cinema.getId());
        if(resultList.size() > 0) {
            throw new ResourceAlreadyExistException("Conflict with an existing schedule.");
        }

        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);
        return scheduleRepo.save(schedule);
    }

    @Override
    public List<Schedule> fetchAllSchedule(String dateString, Long movieId) {

        setStartAndEndDate(dateString);

        if(startDate == null && endDate == null && movieId == null) {
            return (List<Schedule>) scheduleRepo.findAll();
        } else if(startDate == null && endDate == null) {
            return scheduleRepo.findAllByMovieId(movieId);
        }
        else if(movieId == null) {
            return scheduleRepo.findAllByStartDateTimeBetween(startDate, endDate);
        } else {
            return scheduleRepo.findAllByStartDateTimeBetweenAndMovieId(startDate, endDate, movieId);
        }
    }

    @Override
    public Page<Schedule> fetchPaginatedSchedule(String dateString, Long movieId, Integer offset, Integer max) {

        setStartAndEndDate(dateString);

        if(startDate == null && endDate == null && movieId == null) {
            return scheduleRepo.findAll(new PageRequest(offset, max));
        }  else if(startDate == null && endDate == null) {
            return scheduleRepo.findAllByMovieId(movieId, new PageRequest(offset, max));
        } else if(movieId == null) {
            return scheduleRepo.findAllByStartDateTimeBetween(startDate, endDate, new PageRequest(offset, max));
        } else {
            return scheduleRepo.findAllByStartDateTimeBetweenAndMovieId(startDate, endDate, movieId, new PageRequest(offset, max));
        }
    }

    @Override
    public Schedule updateSchedule(Schedule scheduleToBeUpdated, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime) {
        scheduleToBeUpdated.setMovie(movie);
        scheduleToBeUpdated.setCinema(cinema);
        scheduleToBeUpdated.setStartDateTime(startDateTime);
        scheduleToBeUpdated.setEndDateTime(endDateTime);
        return scheduleRepo.save(scheduleToBeUpdated);
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        scheduleRepo.delete(schedule);
    }

    @Override
    public void setScheduleRepo(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    private void setStartAndEndDate(String dateString) {
        try {
            if(dateString != null) {
                String startDateString = dateString.substring(0, 10) + " 00:00:00";
                String endDateString = dateString.substring(0, 10) + " 23:59:00";
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDateString);
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDateString);
            }
        } catch (Exception e) {}
    }
}
