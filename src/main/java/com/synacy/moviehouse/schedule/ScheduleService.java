package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by banjoe on 5/15/17.
 */
public interface ScheduleService {
    Schedule fetchScheduleById(Long id);
    Schedule saveNewSchedule(Movie movie, Cinema cinema, Date startDateTime, Date endDateTime);
    List<Schedule> fetchAllSchedule(String date, Long movieId);
    Page<Schedule> fetchPaginatedSchedule(String date, Long movieId, Integer offset, Integer max);
    Schedule updateSchedule(Schedule scheduleToBeUpdated, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime);
    void deleteSchedule(Schedule schedule);
    void setScheduleRepo(ScheduleRepo scheduleRepo);
}
