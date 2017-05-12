package com.synacy.moviehouse.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Movie fetchById(Long id) {
        return movieRepository.findOne(id);
    }

    public List<Movie> fetchAll() {
        return (List) movieRepository.findAll();
    }

    public Movie createMovie(String name, String genre, Integer duration, String description) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie movieToBeUpdated, String name, String genre, Integer duration, String description) {
        movieToBeUpdated.setName(name);
        movieToBeUpdated.setGenre(genre);
        movieToBeUpdated.setDuration(duration);
        movieToBeUpdated.setDescription(description);
        return movieRepository.save(movieToBeUpdated);
    }

    public void deleteMovie(Movie movieToBeDeleted) {
        movieRepository.delete(movieToBeDeleted);
    }
}
