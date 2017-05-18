package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.MissingParameterException;
import com.synacy.moviehouse.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by banjoe on 5/15/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTest {

    private MovieController movieController;
    @Mock MovieService movieService;

    @Before
    public void setUp() throws Exception {
        movieController = new MovieController();
        movieController.setMovieService(movieService);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchMovieById_shouldThrowResourceNotFoundExceptionWhenMovieIsReturnNull() throws Exception {
        Long movieId = 200L;

        movieController.fetchMovieById(movieId);
        verify(movieService, times(1)).fetchMovieById(eq(movieId));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchAllMovie_shouldThrowResourceNotFoundExceptionWhenListReturnsEmpty() throws Exception {
        String name = "Movie";
        String genre = "Horror";

        movieController.fetchAllMovie(null, null, name, genre);
        verify(movieService, times(1)).fetchAllMovie(eq(name), eq(genre));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchAllMovie_shouldThrowResourceNotFoundExceptionWhenPageReturnsEmpty() throws Exception {
        String name = "Movie";
        String genre = "Horror";
        Integer offset = 0, max = 2;
        List<Movie> list = new ArrayList<>();
        when(movieService.fetchPaginatedMovie(name, genre, offset, max)).thenReturn(new PageImpl<Movie>(list, new PageRequest(offset, max), list.size()));

        movieController.fetchAllMovie(null, null, name, genre);
        verify(movieService, times(1)).fetchAllMovie(eq(name), eq(genre));
    }


    @Test
    public void createMovie_shouldOnlyInvokeOnce() throws Exception {
        Movie movie = new Movie();
        movie.setName("Movie");
        movie.setDescription("Sample Description");
        movie.setDuration(2);
        movie.setGenre("Horror");

        movieController.createMovie(movie);
        verify(movieService, times(1)).saveNewMovie(movie.getName(), movie.getGenre(), movie.getDuration(), movie.getDescription());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateMovie_shouldThrowResourceNotFoundExceptionWhenMovieIsReturnNull() throws Exception {
        Long movieId = 200L;

        movieController.updateMovie(movieId, new Movie());
        verify(movieService, times(1)).fetchMovieById(eq(movieId));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteMovie_shouldThrowResourceNotFoundExceptionWhenMovieIsReturnNull() throws Exception {
        Long movieId = 200L;

        movieController.deleteMovie(movieId);
        verify(movieService, times(1)).fetchMovieById(eq(movieId));
    }

    @Test(expected = MissingParameterException.class)
    public void fetchAllMovie_shouldThrowMissingParameterExceptionIfOffsetIsNullAndMaxIsNotNull() throws Exception {
        Long movieId = 200L;

        movieController.fetchAllMovie(null, 1, null, null);
        verify(movieService, times(1)).fetchMovieById(eq(movieId));
    }

    @Test(expected = MissingParameterException.class)
    public void fetchAllMovie_shouldThrowMissingParameterExceptionIfOffsetIsNotNullAndMaxIsNull() throws Exception {
        Long movieId = 200L;

        movieController.fetchAllMovie(0, null, null, null);
        verify(movieService, times(1)).fetchMovieById(eq(movieId));
    }


}
