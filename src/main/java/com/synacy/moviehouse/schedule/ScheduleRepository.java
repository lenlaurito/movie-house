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
    List<Schedule> findByStartDateTime(Date startDateTime);

    Page<Schedule> findAllByStartDateTime(Date startDateTime, Pageable pageable);
}
