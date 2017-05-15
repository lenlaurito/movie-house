package com.synacy.moviehouse.movie;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by michael on 5/15/17.
 */
@Service
@Transactional
public class MovieService {

    @Autowired @Setter @Getter
    private MovieRepository movieRepository;

    public List<Movie> fetchMovies(Pageable pageable, String genre, String name) {
        return null;
    }

    public Movie createMovie(Movie movie) {
        return null;
    }

    public Movie fetchMovieById(Long movieId) {
        return null;
    }

    public Movie updateMovie(Movie movie) {
        return null;
    }

    public void deleteMovie(Long movieId) {

    }
}
