package com.synacy.moviehouse.movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

        when(movieService.fetchById(movieId)).thenReturn(movie);

        movieController.fetchMovie(movieId);

        verify(movieService, times(1)).fetchById(movieId);
        assertEquals("Movie", movie.getName());
        assertEquals(Genre.ACTION, movie.getGenre());
        assertEquals((Integer) 128, movie.getDuration());
        assertEquals("Description", movie.getDescription());
    }

    @Test
    public void fetchAllMovies_withNullArguments() throws Exception {
        List<Movie> movies = new ArrayList<>();
        when(movieService.fetchAll()).thenReturn(movies);

        List<Movie> response = movieController.fetchAllMovies(null, null, null, null);
//        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    public void fetchAllMovies_withPagination() throws Exception {
        List<Movie> movies = new ArrayList<>();
        movies.add(mock(Movie.class));
        movies.add(mock(Movie.class));
        movies.add(mock(Movie.class));
        when(movieService.fetchAll()).thenReturn(movies);

        List<Movie> response = movieController.fetchAllMovies(null, null, 0, 2);
//        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    public void createNewMovie() throws Exception {
        Movie newMovie = mock(Movie.class);
        movieController.createNewMovie(newMovie);
        verify(movieService, times(1))
                .create(newMovie);
    }

    @Test
    public void updateMovie() throws Exception {
        Movie movie = mock(Movie.class);
        when(movieService.fetchById(100L)).thenReturn(movie);
        when(movie.getName()).thenReturn("Movie");
        when(movie.getGenre()).thenReturn(Genre.ACTION);
        when(movie.getDuration()).thenReturn(192);
        when(movie.getDescription()).thenReturn("");

        movieController.updateMovie(100L, movie);

        verify(movieService, times(1))
                .update(movie, "Movie", Genre.ACTION, 192, "");
    }

    @Test
    public void deleteMovie() throws Exception {
        Movie movie = mock(Movie.class);
        when(movieService.fetchById(100L)).thenReturn(movie);

        movieController.deleteMovie(100L);

        verify(movieService, times(1))
                .delete(movie);
    }

}