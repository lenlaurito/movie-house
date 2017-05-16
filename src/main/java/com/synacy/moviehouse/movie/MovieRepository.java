package com.synacy.moviehouse.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    List<Movie> findMoviesByNameContainingIgnoreCase(String name);

    List<Movie> findAllByGenre(Genre genre);

    List<Movie> findAllByNameContainingIgnoreCaseAndGenre(String name, Genre genre);

    Page<Movie> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Movie> findAllByGenre(Genre genre, Pageable pageable);

    Page<Movie> findAllByNameContainingIgnoreCaseAndGenre(String name, Genre genre, Pageable pageable);

}
