package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{movieId}")
    public ResponseEntity fetchMovie(@PathVariable(value="movieId") Long movieId) {
        Movie movie =  movieService.fetchMovieById(movieId);
        return ResponseEntity.ok().body(movie);
    }

    @GetMapping
    public ResponseEntity fetchAllMovies(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "genre", required = false) String genre,
                                         @RequestParam(value = "offset", required = false) Integer offset,
                                         @RequestParam(value = "max", required = false) Integer max) {
        if (offset == null && max == null) {
            List<Movie> movies = movieService.fetchAllMovies(name, genre);
            return ResponseEntity.ok().body(movies);
        } else if (offset != null && max != null) {
            Page<Movie> movies = movieService.fetchAllMoviesWithPagination(name, genre, offset, max);
            return ResponseEntity.ok().body(movies);
        } else {
            throw new InvalidRequestException("Offset and max should both be used as parameters.");
        }
    }

    @PostMapping
    public ResponseEntity createNewMovie(@RequestBody Movie movieRequest) {
        Movie movie = movieService.createMovie(movieRequest.getName(), movieRequest.getGenre(),
                movieRequest.getDuration(), movieRequest.getDescription());
         return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity updateMovie(@PathVariable(value="movieId") Long movieId,
                                      @RequestBody Movie movieRequest) {
        Movie movieToUpdate = movieService.fetchMovieById(movieId);
        Movie movie = movieService.updateMovie(movieToUpdate, movieRequest.getName(), movieRequest.getGenre(),
                movieRequest.getDuration(), movieRequest.getDescription());
        return ResponseEntity.ok().body(movie);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity deleteMovie(@PathVariable(value="movieId") Long movieId) {
        Movie movie = movieService.fetchMovieById(movieId);
        movieService.deleteMovie(movie);
        return ResponseEntity.noContent().build();
    }

}
