package com.synacy.moviehouse.cinema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by michael on 5/15/17.
 */
public interface CinemaRepository extends PagingAndSortingRepository<Cinema, Long> {
    Page<Cinema> findAllCinemaByTypeContaining(String type, Pageable pageable);
}
