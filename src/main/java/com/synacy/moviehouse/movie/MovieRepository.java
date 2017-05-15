package com.synacy.moviehouse.movie;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * Created by michael on 5/15/17.
 */
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Pageable findAllWithGenreAndName(Pageable pageable, String genre, String name);
}
