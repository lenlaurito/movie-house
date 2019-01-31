package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.MovieAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movie")
    public Movie createNewMovie(@RequestBody Movie movie) throws MovieAlreadyExistsException {
        return movieService.createNewMovie(movie);
    }

    @PutMapping("/movie/{id}")
    public void updateMovie(@RequestBody Movie movie, @PathVariable long id) {

    }
}
