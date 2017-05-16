package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.movie.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

    List<Schedule> findAllByMovie(Movie movie);

    @Query("from Schedule where start_date_time = :startDateTime")
    List<Schedule> findAllByStartDateTime(@Param("startDateTime") Date startDateTime);

    List<Schedule> findAllByStartDateTimeAndMovie(Date startDateTime, Movie movie);

    Page<Schedule> findAllByMovie(Movie movie, Pageable pageable);

    Page<Schedule> findAllByStartDateTime(Date startDateTime, Pageable pageable);

    Page<Schedule> findAllByStartDateTimeAndMovie(Date startDateTime, Movie movie, Pageable pageable);

    @Query("from Schedule where cinema_id = :cinemaId and :startDateTime >= start_date_time and end_date_time >= :endDateTime")
    List<Schedule> findAllByDateWithinRange(@Param("cinemaId") Long cinemaId,
                                            @Param("startDateTime") Date startDateTime,
                                            @Param("endDateTime") Date endDateTime);
}
