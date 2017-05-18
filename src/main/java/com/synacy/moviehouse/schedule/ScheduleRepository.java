package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

    List<Schedule> findAllByStartDateTimeBetween(Date begDateTime, Date endDateTime);

    List<Schedule> findAllByMovie(Movie movie);

    List<Schedule> findAllByMovieAndStartDateTimeBetween(Movie movie, Date begDateTime, Date endDateTime);

    Page<Schedule> findAllByStartDateTimeBetween(Date begDateTime, Date endDateTime, Pageable pageable);

    Page<Schedule> findAllByMovie(Movie movie, Pageable pageable);

    Page<Schedule> findAllByMovieAndStartDateTimeBetween(Movie movie, Date begDateTime, Date endDateTime, Pageable pageable);

    List<Schedule> findAllByCinemaAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(Cinema cinema, Date startDateTime, Date endDateTime);

}
