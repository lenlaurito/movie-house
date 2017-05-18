package com.synacy.moviehouse.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by banjoe on 5/12/17.
 */

public interface MovieRepo extends PagingAndSortingRepository<Movie, Long> {
    List<Movie> findAllByName(String name);
    List<Movie> findAllByGenre(String genre);
    List<Movie> findAllByNameAndGenre(String name, String genre);
    Page<Movie> findAllByGenre(String genre, Pageable pageable);
    Page<Movie> findAllByName(String name, Pageable pageable);
    Page<Movie> findAllByNameAndGenre(String name, String genre, Pageable pageable);
}
