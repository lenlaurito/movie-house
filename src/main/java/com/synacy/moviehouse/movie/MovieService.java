package com.synacy.moviehouse.movie;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by banjoe on 5/12/17.
 */
public interface MovieService {
    Movie fetchMovieById(Long id);
    Movie saveNewMovie(String name, String genre, Integer duration, String description);
    List<Movie> fetchAllMovie(String name, String genre);
    Page<Movie> fetchPaginatedMovie(String name, String genre, Integer offset, Integer max);
    Movie updateMovie(Movie movieToBeUpdated, String name, String genre, Integer duration, String description);
    void deleteMovie(Movie movie);
    void setMovieRepo(MovieRepo movieRepo);
}
