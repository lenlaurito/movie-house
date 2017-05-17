package com.synacy.moviehouse.movie;

import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {

    Movie fetchMovieById(Long id);

    List<Movie> fetchAllMovies(String name, String genre);

    Page<Movie> fetchAllMoviesWithPagination(String name, String genre, Integer offset, Integer max);

    Movie createMovie(String name, Genre genre, Integer duration, String description);

    Movie updateMovie(Movie movie, String name, Genre genre, Integer duration, String description);

    void deleteMovie(Movie movie);

}
