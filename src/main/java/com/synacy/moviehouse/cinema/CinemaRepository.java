package com.synacy.moviehouse.cinema;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */
public interface CinemaRepository extends CrudRepository<Cinema, Long>{
    List<Cinema> findAllByType(CinemaType cinemaType);
}
