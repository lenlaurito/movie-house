package com.synacy.moviehouse.schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

    List<Schedule> findAllByStartDateTimeBetween(Date startOfDay, Date endOfDay);
    List<Schedule> findAllByMovieId(Long movieId);
    List<Schedule> findAllByStartDateTimeBetweenAndMovieId(Date startOfDay, Date endOfDay, Long MovieId);

    Page<Schedule> findAllByStartDateTimeBetween(Date startOfDay, Date endOfDay, Pageable pageable);
    Page<Schedule> findAllByMovieId(Long movieId, Pageable pageable);
    Page<Schedule> findAllByStartDateTimeBetweenAndMovieId(Date startOfDay, Date endOfDay, Long movieId, Pageable pageable);
}
