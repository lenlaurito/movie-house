package com.synacy.moviehouse.movie;

import java.util.List;

public interface MovieService {

    Movie fetchById(Long id);

    List<Movie> fetchAll();

    List<Movie> fetchAll(String name, String genre, Integer offset, Integer max);

    Movie create(Movie movie);

    Movie update(Movie movie, String name, Genre genre, Integer duration, String description);

    void delete(Movie movie);

}
