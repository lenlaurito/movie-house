package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exceptions.NoContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */

@Service
@Transactional
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public List<Movie> fetchAll(String name, GenreType genre) {
        List<Movie> movies;

        if(name != null && genre == null)
            movies = movieRepository.findByName(name);
        else if(name == null && genre != null)
            movies = movieRepository.findByGenre(genre);
        else if(name != null && genre != null)
            movies = movieRepository.findByNameAndGenre(name,genre);
        else
            movies = (List) movieRepository.findAll();

        if(movies.isEmpty())
            throw new NoContentException("No movies exist.");
        else
            return movies;
    }

    public Movie fetchById(Long id) {
        Movie movie = movieRepository.findOne(id);

        if(movie == null) {
            throw new NoContentException("Movie does not exist.");
        }else {
            return movie;
        }
    }

    public Movie createMovie(String name, GenreType genre, Integer duration, String description) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie movieToBeUpdated, String name, GenreType genre, Integer duration, String description) {
        movieToBeUpdated.setName(name);
        movieToBeUpdated.setGenre(genre);
        movieToBeUpdated.setDuration(duration);
        movieToBeUpdated.setDescription(description);
        return movieRepository.save(movieToBeUpdated);
    }

    public void deleteMovie(Movie movieToBeDeleted) {
        movieRepository.delete(movieToBeDeleted);
    }

    public Page<Movie> fetchAllPaginated(String name, GenreType genre, Integer offset, Integer max) {

        Page<Movie> pages;

        if(name != null && genre == null)
            pages = movieRepository.findAllByName(name, new PageRequest(offset, max));
        else if(name == null && genre != null)
            pages = movieRepository.findAllByGenre(genre, new PageRequest(offset, max));
        else if(name != null && genre != null)
            pages = movieRepository.findAllByNameAndGenre(name,genre,new PageRequest(offset, max));
        else
            pages = movieRepository.findAll(new PageRequest(offset, max));

        if(pages.getTotalPages() == 0)
            throw new NoContentException("No movies exist.");
        else
            return pages;
    }

}
