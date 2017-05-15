package com.synacy.moviehouse.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by michael on 5/15/17.
 */
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Page<Movie> findMoviesByGenreContainingAndNameContaining(String genre, String name, Pageable pageable);
}
