package com.synacy.moviehouse.movie;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by michael on 5/15/17.
 */
@Service
@Transactional
public class MovieService {

    @Autowired @Setter @Getter
    private MovieRepository movieRepository;

    public List<Movie> fetchMovies(Pageable pageable, String genre, String name) throws Exception {
        Page<Movie> moviePage = null;

        if (genre == null && name == null)
            moviePage = movieRepository.findAll(pageable);
        else
            moviePage = movieRepository.findMoviesByGenreContainingAndNameContaining(genre, name, pageable);

        if (moviePage.getContent().size() == 0)
            throw new NoResultException();

        return moviePage.getContent();
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie fetchMovieById(Long movieId) {
        Movie movie = movieRepository.findOne(movieId);

        if (movie == null)
            throw new NoResultException();

        return movie;
    }

    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long movieId) {
        Movie movie = fetchMovieById(movieId);

        movieRepository.delete(movie);
    }
}
