package com.synacy.moviehouse.cinema;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by banjoe on 5/12/17.
 */
public interface CinemaRepo extends CrudRepository<Cinema,Long> {
    List<Cinema> findAllByType(String type);
}
