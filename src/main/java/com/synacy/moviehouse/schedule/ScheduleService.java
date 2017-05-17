package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    Schedule fetchScheduleById(Long id);

    List<Schedule> fetchAllSchedules(Date date, Long movieId);

    Page<Schedule> fetchAllSchedulesWithPagination(Date date, Long movieId, Integer offset, Integer max);

    Schedule createSchedule(Movie movie, Cinema cinema, Date startDateTime, Date endDateTime);

    Schedule updateSchedule(Schedule schedule, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime);

    void deleteSchedule(Schedule schedule);

}
