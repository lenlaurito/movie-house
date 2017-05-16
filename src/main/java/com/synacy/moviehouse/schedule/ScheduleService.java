package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.exceptions.InvalidMovieTimeSlotException;
import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.exceptions.NoContentException;
import com.synacy.moviehouse.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @PersistenceContext
    EntityManager entityManager;


    public List<Schedule> fetchAll(Date date) {
        List<Schedule> schedules;

        if(date != null)
            schedules = scheduleRepository.findByStartDateTime(date);
        else
            schedules = (List) scheduleRepository.findAll();

        if(schedules.isEmpty())
            throw new NoContentException("No schedules exist.");
        else
            return schedules;
    }

    public Schedule fetchById(Long id) {
        Schedule schedule = scheduleRepository.findOne(id);

        if(schedule == null) {
            throw new NoContentException("Schedule does not exist.");
        }else {
            return schedule;
        }
    }

    public Schedule createSchedule(Movie movie, Cinema cinema, Date startDateTime, Date endDateTime) {
        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        if(isValidSchedule(schedule.getMovie().getDuration(),schedule.getStartDateTime(),schedule.getEndDateTime()))
            return  scheduleRepository.save(schedule);
        else {
            throw new InvalidMovieTimeSlotException("The schedule is in conflict with the movie's duration.");
        }

    }

    public Schedule updateSchedule(Schedule scheduleToBeUpdated, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime) {
        scheduleToBeUpdated.setMovie(movie);
        scheduleToBeUpdated.setCinema(cinema);
        scheduleToBeUpdated.setStartDateTime(startDateTime);
        scheduleToBeUpdated.setEndDateTime(endDateTime);

        if(isValidSchedule(scheduleToBeUpdated.getMovie().getDuration(),scheduleToBeUpdated.getStartDateTime(),scheduleToBeUpdated.getEndDateTime())) {
            return scheduleRepository.save(scheduleToBeUpdated);
        }
        else {
            throw new InvalidMovieTimeSlotException("The schedule is in conflict with the movie's duration.");
        }
    }

    public void deleteSchedule(Schedule scheduleToBeDeleted) {
        scheduleRepository.delete(scheduleToBeDeleted);
    }

    public Boolean isValidSchedule(int duration, Date startTime, Date endTime) {
        int minutesFromStartTimeToEndTime = ((int) (endTime.getTime() - startTime.getTime()))/60000;
        if(minutesFromStartTimeToEndTime >= duration) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean scheduleConflicts(Cinema cinema, Date startDateTime, Date endDateTime) {
        List<Schedule> list = entityManager.createQuery("from Schedule where (cinema_id = :cinema_id) and (endDateTime <= :startDateTime or startDateTime >= :endDateTime)", Schedule.class)
                .setParameter("cinema_id", cinema.getId())
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
        if(scheduleIsEmpty()) {
            return false;
        } else {
            if(list.isEmpty()) {
                return true;
            }else {
                return false;
            }
        }
    }

    private Boolean scheduleIsEmpty() {
        List<Schedule> list = entityManager.createQuery("from Schedule", Schedule.class).getResultList();
        if(list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Page<Schedule> fetchAllPaginated(Date date, Integer offset, Integer max) {

        Page<Schedule> pages;

        if(date != null)
            pages = scheduleRepository.findAllByStartDateTime(date, new PageRequest(offset, max));
        else
            pages = scheduleRepository.findAll(new PageRequest(offset, max));

        if(pages.getTotalPages() == 0)
            throw new NoContentException("No schedules exist.");
        else
            return pages;
    }
}
