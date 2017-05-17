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

    @Query(value = "select count(*) = 0 from Schedule " +
            "where (cast(?1 as timestamp), cast(?2 as timestamp)) overlaps (start_date_time, end_date_time) " +
            "and cinema_id = ?3",
            nativeQuery = true)
    boolean isScheduleAvailable(Date start, Date end, Long cinemaId);

    @Query(value = "select count(*) = 0 from Schedule " +
                    "where (cast(?1 as timestamp), cast(?2 as timestamp)) overlaps (start_date_time, end_date_time) " +
                    "and cinema_id = ?3 and case when ?4 is not null and ?4 != id then true else false end",
            nativeQuery = true)
    boolean isScheduleAvailable(Date start, Date end, Long cinemaId, Long id);
}
