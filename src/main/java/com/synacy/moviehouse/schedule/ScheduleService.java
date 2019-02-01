package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.exception.ScheduleAlreadyExistsException;
import com.synacy.moviehouse.exception.ScheduleConflictException;
import com.synacy.moviehouse.exception.ScheduleNotFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private MovieRepository movieRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, MovieRepository movieRepository) {
        this.scheduleRepository = scheduleRepository;
        this.movieRepository = movieRepository;
    }

    public Schedule createNewSchedule(Schedule schedule) throws ScheduleAlreadyExistsException, ScheduleConflictException{
        Schedule newSchedule = validateIfScheduleAlreadyExists(schedule);
        newSchedule = validateIfNoScheduleConflict(newSchedule);

        return scheduleRepository.save(newSchedule);
    }

    public void updateSchedule(Schedule schedule, long id) throws ScheduleNotFoundException {
        Schedule newSchedule = validateIfScheduleDoesNotExist(schedule, id);

        scheduleRepository.save(newSchedule);
    }

    public void deleteSchedule(long id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesByMovie(long movieId) {
        Optional <Movie> movie = movieRepository.findById(movieId);
        return scheduleRepository.findByMovie(movie.get());
    }

    public List<Schedule> getSchedulesByDay(Date startDate, Date endDate) {
        return scheduleRepository.findAllByStartDateTimeBetween(startDate, endDate);
    }

    public Schedule getScheduleById(long id) {
        Optional <Schedule> optionalSchedule = scheduleRepository.findById(id);

        return optionalSchedule.get();
    }

    public Schedule validateIfScheduleAlreadyExists(Schedule schedule) throws ScheduleAlreadyExistsException {
        Optional <Schedule> optionalSchedule = scheduleRepository.findById(schedule.getId());

        if (optionalSchedule.isPresent())
            throw new ScheduleAlreadyExistsException();

        return schedule;
    }

    public Schedule validateIfScheduleDoesNotExist(Schedule schedule, long id) throws ScheduleNotFoundException {
        Optional <Schedule> optionalSchedule = scheduleRepository.findById(id);

        if (optionalSchedule.isEmpty())
            throw new ScheduleNotFoundException();

        return schedule;
    }

    public Schedule validateIfNoScheduleConflict(Schedule schedule) throws ScheduleConflictException{

        long timeDiff = TimeUnit.MILLISECONDS.toMinutes( schedule.getEndDateTime().getTime() - schedule.getStartDateTime().getTime() );

        System.out.println(timeDiff);
        System.out.println(schedule.getMovie().getDuration());

        if (timeDiff <= schedule.getMovie().getDuration())
            throw new ScheduleConflictException();

        return schedule;
    }
}
