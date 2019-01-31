package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.MovieAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie createNewMovie(Movie movie) throws MovieAlreadyExistsException {
        Movie newMovie = validateIfMovieAlreadyExists(movie);

        return movieRepository.save(newMovie);
    }

    public void updateMovie(Movie movie) {

    }

    public Movie validateIfMovieAlreadyExists(Movie movie) throws MovieAlreadyExistsException {
        Optional <Movie> optionalMovie = movieRepository.findById(movie.getId());

        if (optionalMovie.isPresent())
            throw new MovieAlreadyExistsException();

        return movie;
    }
}
