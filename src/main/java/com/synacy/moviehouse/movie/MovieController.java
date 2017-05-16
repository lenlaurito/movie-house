package com.synacy.moviehouse.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<Movie> fetchAllMovies(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "genre", required = false) String genre,
                                         @RequestParam(value = "offset", required = false) Integer offset,
                                         @RequestParam(value = "max", required = false) Integer max) {
        if (genre == null && name == null && offset == null && max == null) {
            return movieService.fetchAll();
        } else {
            return movieService.fetchAll(name, genre, offset, max);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createNewMovie(@RequestBody Movie movie) {
        return  movieService.create(movie);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{movieId}")
    public Movie updateMovie(@PathVariable(value="movieId") Long movieId,
                             @RequestBody Movie movieRequest) {

        Movie movie = movieService.fetchById(movieId);
        return movieService.update(movie, movieRequest.getName(), movieRequest.getGenre(),
                movieRequest.getDuration(), movieRequest.getDescription());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable(value="movieId") Long movieId) {
        Movie movie = movieService.fetchById(movieId);
        movieService.delete(movie);
    }

}
