package com.synacy.moviehouse.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by banjoe on 5/12/17.
 */

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepo movieRepo;

    @Override
    public Movie fetchMovieById(Long id) {
        return movieRepo.findOne(id);
    }

    @Override
    public Movie saveNewMovie(String name, String genre, Integer duration, String description) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);
        return movieRepo.save(movie);
    }

    @Override
    public List<Movie> fetchAllMovie(String name, String genre) {
        if(name == null && genre == null) {
            return (List<Movie>) movieRepo.findAll();
        } else if(name == null) {
            return movieRepo.findAllByGenre(genre);
        } else if(genre == null) {
            return movieRepo.findAllByName(name);
        } else {
            return movieRepo.findAllByNameAndGenre(name, genre);
        }
    }

    @Override
    public Page<Movie> fetchPaginatedMovie(String name, String genre, Integer offset, Integer max) {
        if(name == null && genre == null) {
            return movieRepo.findAll(new PageRequest(offset, max));
        } else if(name == null) {
            return movieRepo.findAllByGenre(genre, new PageRequest(offset, max));
        } else if(genre == null) {
            return movieRepo.findAllByName(name, new PageRequest(offset, max));
        } else {
            return movieRepo.findAllByNameAndGenre(name, genre, new PageRequest(offset, max));
        }
    }

    @Override
    public Movie updateMovie(Movie movieToBeUpdated, String name, String genre, Integer duration, String description) {
        movieToBeUpdated.setName(name);
        movieToBeUpdated.setGenre(genre);
        movieToBeUpdated.setDuration(duration);
        movieToBeUpdated.setDescription(description);
        return movieRepo.save(movieToBeUpdated);
    }

    @Override
    public void deleteMovie(Movie movie) {
        movieRepo.delete(movie);
    }

    @Override
    public void setMovieRepo(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }


}
