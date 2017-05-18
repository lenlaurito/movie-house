package com.synacy.moviehouse.movie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

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
        Movie movie = new Movie();
        movie.setName("Movie 1");
        movie.setGenre("Horror");
        movie.setDuration(2);
        movie.setDescription("Sample Description");

        when(movieRepo.save(eq(movie))).thenReturn(movie);

        Movie result = movieService.saveNewMovie("Movie 1", "Horror", 2, "Sample Description");

        verify(movieRepo, times(1)).save(eq(new Movie()));
        Assert.assertEquals(movie, result);
    }

    @Test
    public void fetchAllMovie_shouldReturnAllMovieWithNoSpecificFilter() throws Exception {
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        when(movieRepo.findAll()).thenReturn(movieList);

        List<Movie> resultList = movieService.fetchAllMovie(null, null);

        verify(movieRepo, times(1)).findAll();
        Assert.assertEquals(movieList, resultList);
    }

    @Test
    public void fetchAllMovie_shouldReturnAllMovieWithFilteredByName() throws Exception {
        Movie movie1 = new Movie();
        movie1.setName("Avengers");
        Movie movie2 = new Movie();
        movie2.setName("Avengers");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        when(movieRepo.findAllByName("Avengers")).thenReturn(movieList);

        List<Movie> resultList = movieService.fetchAllMovie("Avengers", null);

        verify(movieRepo, times(1)).findAllByName("Avengers");
        Assert.assertEquals(movieList, resultList);
    }

    @Test
    public void fetchAllMovie_shouldReturnAllMovieWithFilteredByGenre() throws Exception {
        Movie movie1 = new Movie();
        movie1.setGenre("Horror");
        Movie movie2 = new Movie();
        movie2.setGenre("Horror");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        when(movieRepo.findAllByGenre("Horror")).thenReturn(movieList);

        List<Movie> resultList = movieService.fetchAllMovie(null, "Horror");

        verify(movieRepo, times(1)).findAllByGenre("Horror");
        Assert.assertEquals(movieList, resultList);
    }

    @Test
    public void fetchAllMovie_shouldReturnAllMovieWithFilteredByNameAndGenre() throws Exception {
        Movie movie1 = new Movie();
        movie1.setName("Avengers");
        movie1.setGenre("Horror");
        Movie movie2 = new Movie();
        movie2.setName("Avengers");
        movie2.setGenre("Horror");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        when(movieRepo.findAllByNameAndGenre("Avengers", "Horror")).thenReturn(movieList);

        List<Movie> resultList = movieService.fetchAllMovie("Avengers", "Horror");

        verify(movieRepo, times(1)).findAllByNameAndGenre("Avengers", "Horror");
        Assert.assertEquals(movieList, resultList);
    }

    @Test
    public void fetchPaginatedMovie_shouldReturnAPaginatedListWithNoSpecificFilter() throws Exception {
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        Page<Movie> moviePageList = new PageImpl<>(movieList, new PageRequest(0, 2), movieList.size());


        when(movieRepo.findAll(eq(new PageRequest(0, 1)))).thenReturn(moviePageList);

        Page<Movie> resultList = movieService.fetchPaginatedMovie(null, null, 0, 1);

        verify(movieRepo, times(1)).findAll(eq(new PageRequest(0, 1)));
        Assert.assertEquals(moviePageList, resultList);
    }

    @Test
    public void fetchPaginatedMovie_shouldReturnAPaginatedListFilteredByName() throws Exception {
        Movie movie1 = new Movie();
        movie1.setName("Avengers");
        Movie movie2 = new Movie();
        movie2.setName("Avengers");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        Page<Movie> moviePageList = new PageImpl<>(movieList, new PageRequest(0, 2), movieList.size());


        when(movieRepo.findAllByName(eq("Avengers"), eq(new PageRequest(0, 1)))).thenReturn(moviePageList);

        Page<Movie> resultList = movieService.fetchPaginatedMovie("Avengers", null, 0, 1);

        verify(movieRepo, times(1)).findAllByName("Avengers", new PageRequest(0, 1));
        Assert.assertEquals(moviePageList, resultList);
    }

    @Test
    public void fetchPaginatedMovie_shouldReturnAPaginatedListFilteredByGenre() throws Exception {
        Movie movie1 = new Movie();
        movie1.setGenre("Horror");
        Movie movie2 = new Movie();
        movie2.setGenre("Horror");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        Page<Movie> moviePageList = new PageImpl<>(movieList, new PageRequest(0, 2), movieList.size());


        when(movieRepo.findAllByGenre(eq("Horror"), eq(new PageRequest(0, 1)))).thenReturn(moviePageList);

        Page<Movie> resultList = movieService.fetchPaginatedMovie(null, "Horror", 0, 1);

        verify(movieRepo, times(1)).findAllByGenre("Horror", new PageRequest(0, 1));
        Assert.assertEquals(moviePageList, resultList);
    }


    @Test
    public void fetchPaginatedMovie_shouldReturnAPaginatedListFilteredByNameAndGenre() throws Exception {
        Movie movie1 = new Movie();
        movie1.setName("Avengers");
        movie1.setGenre("Horror");
        Movie movie2 = new Movie();
        movie2.setName("Avengers");
        movie2.setGenre("Horror");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        Page<Movie> moviePageList = new PageImpl<>(movieList, new PageRequest(0, 2), movieList.size());

        when(movieRepo.findAllByNameAndGenre(eq("Avengers"), eq("Horror"), eq(new PageRequest(0, 1)))).thenReturn(moviePageList);

        Page<Movie> resultList = movieService.fetchPaginatedMovie("Avengers", "Horror", 0, 1);

        verify(movieRepo, times(1)).findAllByNameAndGenre("Avengers","Horror", new PageRequest(0, 1));
        Assert.assertEquals(moviePageList, resultList);
    }

    @Test
    public void updateMovie_shouldUpdateTheExistingMovie() throws Exception {
        Movie movie = new Movie();

        when(movieRepo.save(eq(movie))).thenReturn(movie);

        Movie result = movieService.updateMovie(movie,"Movie 1", "Horror", 2, "Sample Description");

        verify(movieRepo, times(1)).save(eq(movie));
        Assert.assertEquals(movie, result);
    }

}
