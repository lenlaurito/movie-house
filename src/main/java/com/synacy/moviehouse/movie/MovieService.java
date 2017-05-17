package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        Page<Movie> moviePage = null;

        if (genre == null && name == null)
            moviePage = movieRepository.findAll(pageable);
        else
            moviePage = movieRepository.findMoviesByGenreContainingAndNameContaining(genre, name, pageable);

        List<Movie> movies = moviePage.getContent();

        if (movies.size() == 0)
            throw new NotFoundException("Empty results found.");

        return movies;
    }

    public Movie createMovie(String name, String genre, Integer duration, String description) {
        Movie movie = new Movie();

        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);

        return movieRepository.save(movie);
    }

    public Movie fetchMovieById(Long movieId) {
        Movie movie = movieRepository.findOne(movieId);

        if (movie == null)
            throw new NotFoundException("Movie not found");

        return movie;
    }

    public Movie updateMovie(Long movieId, String name, String genre, Integer duration, String description) {
        Movie movie = fetchMovieById(movieId);

        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);

        return movieRepository.save(movie);
    }

    public void deleteMovie(Long movieId) {
        Movie movie = fetchMovieById(movieId);

        movieRepository.delete(movie);
    }
}
