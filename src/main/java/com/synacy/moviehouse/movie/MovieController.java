package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exceptions.MissingRequiredFieldsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */

@RestController
@RequestMapping(value = "api/v1/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping
    public ResponseEntity fetchAllMovies(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "genre", required = false) GenreType genre,
                                      @RequestParam(value = "offset", required = false) Integer offset,
                                      @RequestParam(value = "max", required = false) Integer max) {
        if (offset == null && max == null) {
            List<Movie> movies =  movieService.fetchAll(name, genre);
            return ResponseEntity.ok().body(movies);
        }
        else if(offset != null && max != null) {
            Page<Movie> movies =  movieService.fetchAllPaginated(name, genre, offset, max);
            return ResponseEntity.ok().body(movies);
        }
        else{
            throw new InvalidParameterException("Parameters are incomplete or unacceptable.");
        }
    }

    @GetMapping("/{movieId}")
    public Movie fetchMovie(@PathVariable(value = "movieId") Long movieId) {
        return movieService.fetchById(movieId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createMovie(@RequestBody Movie movieRequest) {
        if(movieRequest.getName() == null || movieRequest.getGenre() == null || movieRequest.getDuration() == null){
            throw new MissingRequiredFieldsException("Some required data is missing.");
        } else {
            return movieService.createMovie(movieRequest.getName(), movieRequest.getGenre(), movieRequest.getDuration(), movieRequest.getDescription());
        }
    }

    @PutMapping("/{movieId}")
    public Movie updateMovie(@PathVariable(value = "movieId") Long movieId,
                             @RequestBody Movie movieRequest) {
        if(movieRequest.getName() == null || movieRequest.getGenre() == null || movieRequest.getDuration() == null) {
            throw new MissingRequiredFieldsException("Some required data is missing.");
        } else {
            Movie movie = movieService.fetchById(movieId);
            return movieService.updateMovie(movie, movieRequest.getName(), movieRequest.getGenre(), movieRequest.getDuration(), movieRequest.getDescription());
        }
    }

    @DeleteMapping("/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable(value = "movieId") Long movieId) {
        Movie movie = movieService.fetchById(movieId);
        movieService.deleteMovie(movie);
    }
}
