package com.synacy.moviehouse.movie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by michael on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MovieControllerTest {

    @Autowired
    MovieController movieController;

    @Mock
    MovieService movieService;

    @Before
    public void setUp() throws Exception {
        movieController = new MovieController();

        movieController.movieService = movieService;
    }

    @Test
    public void fetchMovies() throws Exception {
        String genre = "sample";
        String name  = "sample";

        Sort sort = new Sort(Sort.Direction.ASC, "genre", "name");

        PageRequest pageRequest = new PageRequest(0, 10, sort);

        movieController.fetchMovies(pageRequest, genre, name);

        verify(movieService, times(1)).fetchMovies(ArgumentMatchers.eq(pageRequest),
                ArgumentMatchers.eq(genre), ArgumentMatchers.eq(name));
    }

    @Test
    public void createMovie() throws Exception {
        Movie movie = new Movie();

        movie.setName("sample");
        movie.setGenre("sample");
        movie.setDuration(120);

        movieController.createMovie(movie);

        verify(movieService, times(1)).createMovie(
                ArgumentMatchers.eq(movie.getName()),
                ArgumentMatchers.eq(movie.getGenre()),
                ArgumentMatchers.eq(movie.getDuration()),
                ArgumentMatchers.eq(movie.getDescription())
        );
    }

    @Test
    public void fetchMovieById() throws Exception {
        Long idToFind = new Long(1);

        Movie movie = mock(Movie.class);

        when(movieService.fetchMovieById(idToFind)).thenReturn(movie);

        movieController.fetchMovieById(idToFind);

        verify(movieService, times(1)).fetchMovieById(ArgumentMatchers.eq(idToFind));
    }

    @Test
    public void updateMovie() throws Exception {
        Long idToFind = new Long(1);

        Movie movie = new Movie();

        movie.setName("sample");
        movie.setGenre("sample");
        movie.setDuration(120);

        Movie updatedMovie = movieController.updateMovie(idToFind, movie);

        verify(movieService, times(1)).updateMovie(
                ArgumentMatchers.eq(idToFind),
                ArgumentMatchers.eq(movie.getName()),
                ArgumentMatchers.eq(movie.getGenre()),
                ArgumentMatchers.eq(movie.getDuration()),
                ArgumentMatchers.eq(movie.getDescription())
        );
    }

    @Test
    public void deleteMovie() throws Exception {
        Long idToFind = new Long(1);


        movieController.deleteMovie(idToFind);

        verify(movieService, times(1)).deleteMovie(ArgumentMatchers.eq(idToFind));
    }

}