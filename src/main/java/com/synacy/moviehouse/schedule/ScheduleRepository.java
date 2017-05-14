package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
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
    Page<Schedule> findAllByDate(Date date, Pageable pageable);
    List<Schedule> findAllByCinema(Cinema cinema);
}
