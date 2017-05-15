package com.synacy.moviehouse.schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by michael on 5/15/17.
 */
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {
    Page<Schedule> findAllByStartDateTime(Date date, Pageable pageable);
}
