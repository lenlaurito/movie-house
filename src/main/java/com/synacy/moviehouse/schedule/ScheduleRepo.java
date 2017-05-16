package com.synacy.moviehouse.schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

import java.util.List;

/**
 * Created by banjoe on 5/15/17.
 */

public interface ScheduleRepo extends PagingAndSortingRepository<Schedule, Long> {
    List<Schedule> findAllByStartDateTimeBetweenAndCinemaId(Date startDateTime, Date endDateTime, Long CinemaId);
    List<Schedule> findAllByStartDateTimeBetween(Date startDateTime, Date endDateTime);
    List<Schedule> findAllByMovieId(Long movieId);
    List<Schedule> findAllByStartDateTimeBetweenAndMovieId(Date startDateTime, Date endDateTime, Long MovieId);
    Page<Schedule> findAllByStartDateTimeBetween(Date startDateTime, Date endDateTime, Pageable pageable);
    Page<Schedule> findAllByMovieId(Long movieId, Pageable pageable);
    Page<Schedule> findAllByStartDateTimeBetweenAndMovieId(Date startDateTime, Date dateEndTime, Long movieId, Pageable pageable);
}
