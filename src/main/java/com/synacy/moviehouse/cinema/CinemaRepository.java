package com.synacy.moviehouse.cinema;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by steven on 5/12/17.
 */
public interface CinemaRepository extends PagingAndSortingRepository<Cinema, Long> {

    List<Cinema> findAllByType(CinemaType cinemaType);
}