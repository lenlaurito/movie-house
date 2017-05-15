package com.synacy.moviehouse.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by michael on 5/15/17.
 */
@RestController
@RequestMapping(value = "/api/v1/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> fetchMovies(Pageable pageable,
                                   @RequestParam(value = "genre", required = false) String genre,
                                   @RequestParam(value = "name", required = false) String name) {

        Sort sort = new Sort(Sort.Direction.ASC, "genre", "name");
        PageRequest pageRequest = new PageRequest(pageable.getOffset(), pageable.getPageSize(), sort);

        return movieService.fetchMovies(pageable, genre, name);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createMovie(@RequestBody Movie movie) {

        return movieService.createMovie(movie);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{movieId}")
    public Movie updateMovie(@PathVariable(value = "movieId") Long movieId,
                             @RequestBody Movie movieRequest) {

        Movie movie = movieService.fetchMovieById(movieId);

        movie.setGenre(movieRequest.getGenre());
        movie.setDuration(movieRequest.getDuration());
        movie.setName(movieRequest.getName());
        movie.setDescription(movieRequest.getDescription());

        return movieService.updateMovie(movie);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable(value = "movieId") Long movieId) {
        movieService.deleteMovie(movieId);
    }
}
