package com.synacy.moviehouse.schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by michael on 5/15/17.
 */
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {
    @Query(value = "select s from Schedule s " +
                        "inner join s.movie m on movie_id = m.id " +
                    "where cast(?1 as timestamp) between start_date_time and end_date_time " +
                    " and m.name like %?2%",
        countQuery = "select count(s) from Schedule s " +
                    "inner join s.movie m on movie_id = m.id " +
                    "where cast(?1 as timestamp) between start_date_time and end_date_time " +
                    " and m.name like %?2%")
    Page<Schedule> findAllWithinScheduleAndNameContaining(String date, String name, Pageable pageable);
}
