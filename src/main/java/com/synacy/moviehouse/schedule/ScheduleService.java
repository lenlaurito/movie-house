package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    Schedule fetchById(Long id);

    List<Schedule> fetchAll();

    List<Schedule> fetchAllSchedules(Date date, Long movieId, Integer offset, Integer max);

    Schedule create(Schedule schedule);

    Schedule update(Schedule schedule, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime);

    void delete(Schedule schedule);

}
