package com.synacy.moviehouse.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}")
    public Movie fetchMovie(@PathVariable(value="movieId") Long movieId) {
        return movieService.fetchById(movieId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> fetchAllMovies() {
        return movieService.fetchAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createNewMovie(@RequestBody Movie movieRequest) {
        return  movieService.createMovie(movieRequest.getName(), movieRequest.getGenre(),
                movieRequest.getDuration(), movieRequest.getDescription());
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{movieId}")
    public Movie updateMovie(@PathVariable(value="movieId") Long movieId,
                               @RequestBody Movie movieRequest) {
        Movie movie = movieService.fetchById(movieId);
        return movieService.updateMovie(movie, movieRequest.getName(), movieRequest.getGenre(),
                movieRequest.getDuration(), movieRequest.getDescription());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable(value="movieId") Long movieId) {
        Movie movie = movieService.fetchById(movieId);
        movieService.deleteMovie(movie);
    }
}
