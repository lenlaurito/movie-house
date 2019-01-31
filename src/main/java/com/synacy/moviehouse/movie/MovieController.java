package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.MovieAlreadyExistsException;
import com.synacy.moviehouse.exception.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void updateMovie(@RequestBody Movie movie, @PathVariable long id) throws MovieNotFoundException {
        movieService.updateMovie(movie, id);
    }

    @DeleteMapping("/movie/{id}")
    public void deleteMovie(@PathVariable long id) {
        movieService.deleteMovie(id);
    }

    @GetMapping("/movie")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/movie")
    public List<Movie> getMovieByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }
}
