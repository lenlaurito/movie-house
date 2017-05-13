package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by steven on 5/12/17.
 */

@Service
@Transactional
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Movie fetchById(Long id) {

        Movie movie = movieRepository.findOne(id);

        if(movie == null)
            throw new NoContentFoundException("Not content found");
        else
            return movie;
    }

    public List<Movie> fetchAll(String name, MovieGenre genre) {

        List<Movie> movies;

        if(name != null && genre == null)
            movies = movieRepository.findByName(name);
        else if(name == null && genre != null)
            movies = movieRepository.findByGenre(genre);
        else if(name != null && genre != null)
            movies = movieRepository.findByNameAndGenre(name,genre);
        else
            movies = (List) movieRepository.findAll();

        if(movies.size() < 1)
            throw new NoContentFoundException("Not content found");
        else
            return movies;
    }

    public Page<Movie> fetchAllPaginated(String name, MovieGenre genre, Integer offset, Integer max) {

        Page<Movie> page;

        if(name != null && genre == null)
            page = movieRepository.findAllByName(name, new PageRequest(offset, max));
        else if(name == null && genre != null)
            page = movieRepository.findAllByGenre(genre, new PageRequest(offset, max));
        else if(name != null && genre != null)
            page = movieRepository.findAllByNameAndGenre(name,genre,new PageRequest(offset, max));
        else
            page = movieRepository.findAll(new PageRequest(offset, max));

        if(page.getTotalPages() < 1)
            throw new NoContentFoundException("Not content found");
        else
            return page;
    }

    public Movie createMovie(String name, MovieGenre genre, int duration, String description) {

        Movie movie = new Movie();

        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);

        return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie movie, String name, MovieGenre genre, int duration, String description){

        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);

        return movieRepository.save(movie);
    }

    public void deleteMovie(Movie movie){
        movieRepository.delete(movie);
    }

}
