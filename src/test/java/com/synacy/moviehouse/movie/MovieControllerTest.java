package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.cinema.CinemaType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    MovieController movieController;

    @Test
    public void fetchMovie_shouldGetACorrectMovie() throws Exception {
        Long movieId = 2L;
        Movie movie = new Movie();
        movie.setName("Movie");
        movie.setGenre(Genre.ACTION);
        movie.setDuration(128);
        movie.setDescription("Description");

        when(movieService.fetchMovieById(movieId)).thenReturn(movie);

        ResponseEntity<Movie> response = movieController.fetchMovie(movieId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("Movie", movie.getName());
        assertEquals(Genre.ACTION, movie.getGenre());
        assertEquals((Integer) 128, movie.getDuration());
        assertEquals("Description", movie.getDescription());
    }

    @Test
    public void fetchAllMovies_shouldRetrieveMoviesWithUnspecifiedType() throws Exception {
        Movie movie1 = new Movie();
        movie1.setName("Movie 1");
        movie1.setGenre(Genre.ACTION);
        movie1.setDuration(160);
        movie1.setDescription("Description");

        Movie movie2 = new Movie();
        movie2.setName("Movie 2");
        movie2.setGenre(Genre.ADVENTURE);
        movie2.setDuration(160);
        movie2.setDescription("Description");

        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);

        when(movieService.fetchAllMovies(null, Genre.ACTION.name())).thenReturn(movies);

        ResponseEntity<Movie> response = movieController.fetchAllMovies(null, Genre.ACTION.name(), 0, 2);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
//        assertEquals("Movie 1", response.getBody().getName());
//        assertEquals(Genre.ACTION, response.getBody().getGenre());
//        assertEquals((Integer) 160, response.getBody().getDuration());
//        assertEquals("Description", response.getBody().getDescription());
//        assertEquals("Movie 2", response.getBody().getName());
//        assertEquals(Genre.ADVENTURE, response.getBody().getGenre());
//        assertEquals((Integer) 160, response.getBody().getDuration());
//        assertEquals("Description", response.getBody().getDescription());
    }

    @Test
    public void fetchAllMovies_shouldRetrieveMoviesWithSpecifiedNameAndGenre() throws Exception {
        Movie movie1 = new Movie();
        movie1.setName("Movie 1");
        movie1.setGenre(Genre.ACTION);
        movie1.setDuration(160);
        movie1.setDescription("Description");

        Movie movie2 = new Movie();
        movie2.setName("Movie 2");
        movie2.setGenre(Genre.ADVENTURE);
        movie2.setDuration(160);
        movie2.setDescription("Description");

        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);

        when(movieService.fetchAllMovies(null, Genre.ACTION.name())).thenReturn(movies);

        ResponseEntity<Movie> response = movieController.fetchAllMovies(null, Genre.ACTION.name(), 0, 2);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
//        assertEquals("Movie 1", response.getBody().getName());
//        assertEquals(Genre.ACTION, response.getBody().getGenre());
//        assertEquals((Integer) 160, response.getBody().getDuration());
//        assertEquals("Description", response.getBody().getDescription());
//        assertEquals("Movie 2", response.getBody().getName());
//        assertEquals(Genre.ADVENTURE, response.getBody().getGenre());
//        assertEquals((Integer) 160, response.getBody().getDuration());
//        assertEquals("Description", response.getBody().getDescription());
    }

    @Test
    public void createNewMovie() throws Exception {
        String name = "Movie X";
        Genre genre = Genre.ACTION;
        Integer duration = 160;
        String description = "Description";

        Movie newMovie = new Movie();
        newMovie.setName(name);
        newMovie.setGenre(genre);
        newMovie.setDuration(duration);
        newMovie.setDescription(description);

        when(movieService.createMovie(name, genre, duration, description)).thenReturn(newMovie);

        ResponseEntity<Movie> response = movieController.createNewMovie(newMovie);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(name, response.getBody().getName());
        assertEquals(genre, response.getBody().getGenre());
        assertEquals(duration, response.getBody().getDuration());
        assertEquals(description, response.getBody().getDescription());
    }

    @Test
    public void updateMovie() throws Exception {
        Long movieId = 2L;
        String name = "Movie Z";
        Genre genre = Genre.ACTION;
        Integer duration = 160;
        String description = "Description";

        Movie movieToUpdate = new Movie();
        movieToUpdate.setName(name);
        movieToUpdate.setGenre(genre);
        movieToUpdate.setDuration(duration);
        movieToUpdate.setDescription(description);

        when(movieService.fetchMovieById(movieId)).thenReturn(movieToUpdate);
        when(movieService.updateMovie(movieToUpdate, name, genre, duration, description)).thenReturn(movieToUpdate);

        ResponseEntity<Movie> response = movieController.updateMovie(movieId, movieToUpdate);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(name, response.getBody().getName());
        assertEquals(genre, response.getBody().getGenre());
        assertEquals(duration, response.getBody().getDuration());
        assertEquals(description, response.getBody().getDescription());
    }

    @Test
    public void deleteMovie() throws Exception {
        Long movieId = 100L;

        Movie movieToDelete = new Movie();
        movieToDelete.setName("Cinema Z");
        movieToDelete.setGenre(Genre.ACTION);
        movieToDelete.setDuration(150);
        movieToDelete.setDescription("Anything");

        when(movieService.fetchMovieById(movieId)).thenReturn(movieToDelete);

        ResponseEntity<Movie> response = movieController.deleteMovie(movieId);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
    }

}