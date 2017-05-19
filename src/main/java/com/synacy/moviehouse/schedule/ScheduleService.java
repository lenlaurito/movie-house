package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.exceptions.InvalidMovieTimeSlotException;
import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.exceptions.NoContentException;
import com.synacy.moviehouse.movie.Movie;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */

@Service
@Transactional
public class ScheduleService {

    private Date startOfDay = null;
    private Date endOfDay = null;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hibernateEntityManagerFactory) {
        return hibernateEntityManagerFactory.getSessionFactory();
    }

    @Autowired
    SessionFactory sessionFactory;

    public List<Schedule> fetchAll(String date, Long movieId) {

        setStartAndEndTimeOfDay(date);

        if(startOfDay == null && endOfDay == null && movieId == null) {
            return (List<Schedule>) scheduleRepository.findAll();
        }else if(startOfDay == null && endOfDay == null) {
            return scheduleRepository.findAllByMovieId(movieId);
        }else if(movieId == null) {
            return scheduleRepository.findAllByStartDateTimeBetween(startOfDay, endOfDay);
        }else {
            return scheduleRepository.findAllByStartDateTimeBetweenAndMovieId(startOfDay, endOfDay, movieId);
        }
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
        Session session = this.sessionFactory.openSession();
        Criteria cr = session.createCriteria(Schedule.class);
        cr.add(Restrictions.eq("cinema", cinema));
        cr.add(Restrictions.le("startDateTime", endDateTime));
        cr.add(Restrictions.ge("endDateTime", startDateTime));
        List list = cr.list();
        if(scheduleIsEmpty()) {
            return false;
        } else {
            if(list.isEmpty()) {
                return false;
            }else {
                return true;
            }
        }
    }

    private Boolean scheduleIsEmpty() {
        Session session = this.sessionFactory.openSession();
        List list = session.createQuery("FROM Schedule").list();
        if(list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Page<Schedule> fetchAllPaginated(String date, Long movieId, Integer offset, Integer max) {

        setStartAndEndTimeOfDay(date);

        if(startOfDay == null && endOfDay == null && movieId == null) {
            return scheduleRepository.findAll(new PageRequest(offset, max));
        }else if(startOfDay == null && endOfDay == null) {
            return scheduleRepository.findAllByMovieId(movieId, new PageRequest(offset, max));
        }else if(movieId == null) {
            return scheduleRepository.findAllByStartDateTimeBetween(startOfDay, endOfDay, new PageRequest(offset, max));
        }else {
            return scheduleRepository.findAllByStartDateTimeBetweenAndMovieId(startOfDay, endOfDay, movieId, new PageRequest(offset, max));
        }
    }

    private void setStartAndEndTimeOfDay(String date) {
        try {
            if(date != null) {
                String startOfDayString = date.substring(0, 10) + " 00:00:00";
                String endOfDayString = date.substring(0, 10) + " 23:59:00";
                startOfDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startOfDayString);
                endOfDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endOfDayString);
            }
        }catch (Exception e) {}
    }
}
