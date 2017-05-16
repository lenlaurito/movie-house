package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.NoContentFoundException;
import lombok.Setter;
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

    @Autowired @Setter
    MovieRepository movieRepository;

    public Movie fetchById(Long id) {
        Movie movie = movieRepository.findOne(id);

        if(movie == null)
            throw new NoContentFoundException("Not content found");
        else
            return movie;
    }

    public List<Movie> fetchAll(String name, MovieGenre genre) {

        List<Movie> movieList;

        if(name != null && genre == null)
            movieList = movieRepository.findAllByName(name);
        else if(name == null && genre != null)
            movieList = movieRepository.findAllByGenre(genre);
        else if(name != null && genre != null)
            movieList = movieRepository.findAllByNameAndGenre(name,genre);
        else
            movieList = (List) movieRepository.findAll();

        if(movieList.size() < 1)
            throw new NoContentFoundException("Not content found");
        else
            return movieList;
    }

    public Page<Movie> fetchAllPaginated(String name, MovieGenre genre, Integer offset, Integer max) {

        Page<Movie> moviePage;

        if(name != null && genre == null)
            moviePage = movieRepository.findAllByName(name, new PageRequest(offset, max));
        else if(name == null && genre != null)
            moviePage = movieRepository.findAllByGenre(genre, new PageRequest(offset, max));
        else if(name != null && genre != null)
            moviePage = movieRepository.findAllByNameAndGenre(name,genre,new PageRequest(offset, max));
        else
            moviePage = movieRepository.findAll(new PageRequest(offset, max));

        return moviePage;
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
