package com.synacy.moviehouse.movie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.NoResultException;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by michael on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MovieServiceTest {

    @Autowired
    MovieService movieService;

    @Mock
    MovieRepository movieRepository;

    @Before
    public void setUp() {
        movieService = new MovieService();

        movieService.setMovieRepository(movieRepository);
    }


    @Test
    public void fetchMovies_withNoFilter_movieRepositoryShouldFindAll() throws Exception {
        int page = 0;
        int size = 10;

        Page pageOjb = mock(Page.class);

        List<Movie> movies = mock(List.class);

        movies.add(mock(Movie.class));
        movies.add(mock(Movie.class));


        PageRequest pageRequest = new PageRequest(page, size);

        when(movieRepository.findAll(pageRequest)).thenReturn(pageOjb);
        when(pageOjb.getContent()).thenReturn(movies);

        movieService.fetchMovies(pageRequest, null, null);

        int expectedInvocation = 1;

        verify(movieRepository, times(expectedInvocation)).findAll(ArgumentMatchers.eq(pageRequest));
    }

    @Test
    public void fetchMovies_withFilter_movieRepositoryShouldFindMoviesWithGenreOrName() throws Exception {
        int page = 0;
        int size = 10;
        String genre = "action";
        String name = "matrix";

        Page pageOjb = mock(Page.class);

        List<Movie> movies = mock(List.class);

        movies.add(mock(Movie.class));
        movies.add(mock(Movie.class));


        PageRequest pageRequest = new PageRequest(page, size);

        when(movieRepository.findMoviesByGenreContainingAndNameContaining(genre, name, pageRequest)).thenReturn(pageOjb);
        when(pageOjb.getContent()).thenReturn(movies);

        movieService.fetchMovies(pageRequest, genre, name);

        int expectedInvocation = 1;

        verify(movieRepository, times(expectedInvocation)).findMoviesByGenreContainingAndNameContaining(
                ArgumentMatchers.eq(genre),
                ArgumentMatchers.eq(name),
                ArgumentMatchers.eq(pageRequest));
    }

    @Test
    public void createMovie_shouldSaveMovie() throws Exception {

        String name = "sample";
        String genre = "sample";
        Integer duration = 1;
        String description = "sample";

        movieService.createMovie(name, genre, duration, description);

        int expectedInvocation = 1;

        verify(movieRepository, times(expectedInvocation)).save(Mockito.any(Movie.class));
    }

    @Test
    public void fetchMovieById_noException_shouldFindOneMovie() throws Exception {
        Long idToFind = new Long(1);

        when(movieRepository.findOne(idToFind)).thenReturn(mock(Movie.class));

        movieService.fetchMovieById(idToFind);

        int expectedInvocations = 1;

        verify(movieRepository, times(expectedInvocations)).findOne(ArgumentMatchers.eq(idToFind));
    }

    @Test(expected = NoResultException.class)
    public void fetchMovieById_notExists_shouldThrowException() throws Exception {
        Long idToFind = new Long(-1);

        movieService.fetchMovieById(idToFind);
    }

    @Test
    public void updateMovie() throws Exception {
        Movie movie = mock(Movie.class);

        when(movieRepository.findOne(movie.getId())).thenReturn(movie);

        movieService.updateMovie(movie.getId(), movie.getName(), movie.getGenre(), movie.getDuration(),
                movie.getDescription());

        int expectedInvocation = 1;

        verify(movieRepository, times(expectedInvocation)).save(ArgumentMatchers.eq(movie));
    }

    @Test
    public void deleteMovie_noException_shouldFindAndDeleteMovie() throws Exception {
        Long idToFind = new Long(1);

        Movie movie = mock(Movie.class);

        when(movieRepository.findOne(idToFind)).thenReturn(movie);

        movieService.deleteMovie(idToFind);

        int expectedInvocations = 1;

        verify(movieRepository, times(expectedInvocations)).findOne(ArgumentMatchers.eq(idToFind));
        verify(movieRepository, times(expectedInvocations)).delete(ArgumentMatchers.eq(movie));
    }
}