package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.movie.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

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
}
