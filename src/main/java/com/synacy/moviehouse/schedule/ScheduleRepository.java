package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by steven on 5/12/17.
 */

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule,Long>{
    List<Schedule> findAllByDate(Date date);
    List<Schedule> findAllByMovie(Movie movie);
    List<Schedule> findAllByDateAndMovie(Date date, Movie movie);

    Page<Schedule> findAllByDate(Date date, Pageable pageable);
    Page<Schedule> findAllByMovie(Movie movie, Pageable pageable);
    Page<Schedule> findAllByDateAndMovie(Date date, Movie movie, Pageable pageable);

//    @Query("from Schedule where cinema_id = :cinemaId and :startDateTime >= start_date_time and end_date_time >= :endDateTime")
//    List<Schedule> findAllByDateWithinRange(@Param("cinemaId") Long cinemaId,
//                                            @Param("startDateTime") Date startDateTime,
//                                            @Param("endDateTime") Date endDateTime);
}
