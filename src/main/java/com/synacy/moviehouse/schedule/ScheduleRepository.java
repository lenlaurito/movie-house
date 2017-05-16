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

    @Query("from Schedule where start_date_time between :begDateTime and :endDateTime")
    List<Schedule> findAllByDate(@Param("begDateTime") Date begDateTime,
                                 @Param("endDateTime") Date endDateTime);

    @Query("from Schedule where movie_id = :movieId and start_date_time between :begDateTime and :endDateTime")
    List<Schedule> findAllByDateAndMovie(@Param("begDateTime") Date begDateTime,
                                         @Param("endDateTime") Date endDateTime,
                                         @Param("movieId") Long movieId);

    Page<Schedule> findAllByMovie(Movie movie, Pageable pageable);

    Page<Schedule> findAllByStartDateTime(Date startDateTime, Pageable pageable);

    Page<Schedule> findAllByStartDateTimeAndMovie(Date startDateTime, Movie movie, Pageable pageable);

    @Query("from Schedule where cinema_id = :cinemaId and :startDateTime >= start_date_time and end_date_time >= :endDateTime")
    List<Schedule> findAllByDateWithinRange(@Param("cinemaId") Long cinemaId,
                                            @Param("startDateTime") Date startDateTime,
                                            @Param("endDateTime") Date endDateTime);
}
