package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.InvalidRequestException;
import com.synacy.moviehouse.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Movie fetchMovieById(Long id) {
        Movie movie = movieRepository.findOne(id);
        if (movie == null) {
            throw new ResourceNotFoundException("Movie with id " + id + " does not exist.");
        }
        return movie;
    }

    public List<Movie> fetchAll() {
        return (List) movieRepository.findAll();
    }

    public List<Movie> fetchAllMovies(String name, String genre) {
        List<Movie> movies;
        if (name == null && genre == null) {
            movies = (List) movieRepository.findAll();
        } else if (genre == null) {
            movies = movieRepository.findAllByNameContainingIgnoreCase(name);
        } else if (name == null) {
            movies = movieRepository.findAllByGenre(Genre.valueOf(genre.toUpperCase()));
        } else {
            movies = movieRepository.findAllByNameContainingIgnoreCaseAndGenre(name, Genre.valueOf(genre.toUpperCase()));
        }
        return movies;
    }

    public Page<Movie> fetchAllMoviesWithPagination(String name, String genre, Integer offset, Integer max) {
        Pageable pageable = new PageRequest(offset, max, Sort.Direction.ASC, "name");
        if (name == null && genre == null) {
            return movieRepository.findAll(pageable);
        } else if (name != null) {
            return movieRepository.findAllByNameContainingIgnoreCase(name, pageable);
        } else if (genre != null) {
            return movieRepository.findAllByGenre(Genre.valueOf(genre.toUpperCase()), pageable);
        } else {
            return movieRepository.findAllByNameContainingIgnoreCaseAndGenre(name, Genre.valueOf(genre.toUpperCase()), pageable);
        }
    }

    public Movie createMovie(String name, Genre genre, Integer duration, String description) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie movie, String name, Genre genre, Integer duration, String description) {
        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);
        return movieRepository.save(movie);
    }

    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }

}
