package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.MissingParameterException;
import com.synacy.moviehouse.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by banjoe on 5/12/17.
 */

@RestController
@RequestMapping(value = "/api/v1/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}")
    public Movie fetchMovieById(@PathVariable Long movieId){

        Movie movie = movieService.fetchMovieById(movieId);

        if(movie == null) {
            throw new ResourceNotFoundException("Movie does not exist");
        }

        return movie;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity fetchAllMovie(@RequestParam(value = "offset", required = false) Integer offset,
                                               @RequestParam(value = "max", required = false) Integer max,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "genre", required = false) String genre) {
        if (offset == null && max == null) {
            List<Movie> allMovies = movieService.fetchAllMovie(name, genre);

            if(allMovies.isEmpty()){
                throw new ResourceNotFoundException("No existing Movie found.");
            }

            return ResponseEntity.ok().body(allMovies);
        }
        else if(offset != null && max != null) {
            Page<Movie> paginatedMovie = movieService.fetchPaginatedMovie(name, genre, offset, max);

            if(paginatedMovie.getContent().isEmpty()){
                throw new ResourceNotFoundException("No existing Movie found");
            }

            return ResponseEntity.ok().body(paginatedMovie);
        } else {
            throw new MissingParameterException("Need both offset and max.");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.saveNewMovie(movie.getName(), movie.getGenre(), movie.getDuration(), movie.getDescription());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{movieId}")
    public Movie updateMovie(@PathVariable Long movieId, @RequestBody Movie movieRequest) {
        Movie movie = movieService.fetchMovieById(movieId);

        if(movie == null) {
            throw new ResourceNotFoundException("Movie does not exist");
        }

        return movieService.updateMovie(movie, movieRequest.getName(), movieRequest.getGenre(), movieRequest.getDuration(), movieRequest.getDescription());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable(value="movieId") Long movieId) {
        Movie movie = movieService.fetchMovieById(movieId);

        if (movie == null) {
            throw new ResourceNotFoundException("Movie does not exist");
        }

        movieService.deleteMovie(movie);
    }

}
