package com.synacy.moviehouse.cinema;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CinemaRepository extends CrudRepository<Cinema, Long> {

    List<Cinema> findAllByType(CinemaType type);

}
