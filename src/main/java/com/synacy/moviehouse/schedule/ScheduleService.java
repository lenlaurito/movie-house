package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.movie.MovieService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Schedule> fetchAllSchedules(Pageable pageable, Date date, String name) {
        return null;
    }

    public Schedule createSchedule(Schedule schedule) {
        return null;
    }

    public Schedule fetchSscheduleById(Long scheduleId) {
        return null;
    }

    public Schedule updateSchedule(Long scheduleId, Schedule schedule) {
        return null;
    }

    public void deleteScheduleById(Long scheduleId) {
    }
}
