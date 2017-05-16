package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.cinema.Cinema;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by banjoe on 5/15/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    private MovieService movieService;
    @Mock MovieRepo movieRepo;

    @Before
    public void setUp() throws Exception {
        movieService = new MovieServiceImpl();
        movieService.setMovieRepo(movieRepo);
    }

    @Test
    public void saveNewMovie_shouldSaveNewMovie() throws Exception {
        movieService.saveNewMovie("Movie 1", "Horror", 2, "Sample Description");
        verify(movieRepo, times(1)).save(eq(new Movie()));
    }

    @Test
    public void fetchAllMovie_shouldReturnAllMovieWithNoSpecificFilter() throws Exception {
        movieService.fetchAllMovie(null, null);
        verify(movieRepo, times(1)).findAll();
    }

    @Test
    public void fetchAllMovie_shouldReturnAllMovieWithFilteredByName() throws Exception {
        movieService.fetchAllMovie("Movie", null);
        verify(movieRepo, times(1)).findAllByName("Movie");
    }

    @Test
    public void fetchAllMovie_shouldReturnAllMovieWithFilteredByGenre() throws Exception {
        movieService.fetchAllMovie(null, "Horror");
        verify(movieRepo, times(1)).findAllByGenre("Horror");
    }

    @Test
    public void fetchAllMovie_shouldReturnAllMovieWithFilteredByNameAndGenre() throws Exception {
        movieService.fetchAllMovie("Movie", "Horror");
        verify(movieRepo, times(1)).findAllByNameAndGenre("Movie", "Horror");
    }

    @Test
    public void fetchPaginatedMovie_shouldReturnAPaginatedListWithNoSpecificFilter() throws Exception {
        movieService.fetchPaginatedMovie(null, null, 0, 1);
        verify(movieRepo, times(1)).findAll(eq(new PageRequest(0, 1)));
    }

    @Test
    public void fetchPaginatedMovie_shouldReturnAPaginatedListFilteredByName() throws Exception {
        movieService.fetchPaginatedMovie("Movie", null, 0, 1);
        verify(movieRepo, times(1)).findAllByName("Movie", new PageRequest(0, 1));
    }

    @Test
    public void fetchPaginatedMovie_shouldReturnAPaginatedListFilteredByGenre() throws Exception {
        movieService.fetchPaginatedMovie(null, "Horror", 0, 1);
        verify(movieRepo, times(1)).findAllByGenre("Horror", new PageRequest(0, 1));
    }


    @Test
    public void fetchPaginatedMovie_shouldReturnAPaginatedListFilteredByNameAndGenre() throws Exception {
        movieService.fetchPaginatedMovie("Movie", "Horror", 0, 1);
        verify(movieRepo, times(1)).findAllByNameAndGenre("Movie","Horror", new PageRequest(0, 1));
    }

    @Test
    public void updateMovie_shouldUpdateTheExistingMovie() throws Exception {
        Movie movie = new Movie();

        movieService.updateMovie(movie,"Movie 1", "Horror", 2, "Sample Description");
        verify(movieRepo, times(1)).save(eq(movie));
    }

}
