package com.synacy.moviehouse.movie;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    List<Movie> findByName(String name);
    List<Movie> findByGenre(GenreType genre);
    List<Movie> findByNameAndGenre(String name, GenreType genre);

    Page<Movie> findAllByName(String name, Pageable pageable);
    Page<Movie> findAllByGenre(GenreType genre, Pageable pageable);
    Page<Movie> findAllByNameAndGenre(String name, GenreType genre, Pageable pageable);
}
