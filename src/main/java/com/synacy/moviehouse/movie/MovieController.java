package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.IncompleteInformationException;
import com.synacy.moviehouse.exception.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by steven on 5/12/17.
 */
@RestController
@RequestMapping(value = "/api/v1/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}")
    public Movie fetchMovie(@PathVariable(value = "movieId") Long movieId){
        return movieService.fetchById(movieId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity fetchAllMovie(@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "genre", required = false) MovieGenre genre,
                                        @RequestParam(value = "offset", required = false) Integer offset,
                                        @RequestParam(value = "max", required = false) Integer max){
        if (offset == null && max == null) {
            List<Movie> movieList =  movieService.fetchAll(name, genre);
            return ResponseEntity.ok().body(movieList);
        }
        else if(offset != null && max != null) {
            Page<Movie> moviePage =  movieService.fetchAllPaginated(name, genre, offset, max);
            return ResponseEntity.ok().body(moviePage);
        }
        else{
            throw new InvalidParameterException("Parameters incomplete or isn't acceptable");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createMovie(@RequestBody Movie movie){
        if(movie.getName() == null || movie.getGenre() == null || movie.getDuration() == null)
            throw new IncompleteInformationException("Missing some required information");
        else
            return movieService.createMovie(movie.getName(),movie.getGenre(),movie.getDuration(),movie.getDescription());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{movieId}")
    public Movie updateMovie(@PathVariable(value = "movieId") Long movieId, @RequestBody Movie movie){
        if(movie.getName() == null || movie.getGenre() == null || movie.getDuration() == null)
            throw new IncompleteInformationException("Missing some required information");
        else {
            Movie movieToBeUpdated = movieService.fetchById(movieId);
            return movieService.updateMovie(movieToBeUpdated, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getDescription());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable(value = "movieId") Long movieId){
        Movie movie = movieService.fetchById(movieId);
        movieService.deleteMovie(movie);
    }
}
