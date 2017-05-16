package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.NotFoundException;
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
import java.util.ArrayList;
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

        Page<Movie> pageOjb = mock(Page.class);

        List<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie();
        Movie movie2 = new Movie();

        movies.add(movie1);
        movies.add(movie2);

        PageRequest pageRequest = new PageRequest(page, size);

        when(movieRepository.findAll(pageRequest)).thenReturn(pageOjb);
        when(pageOjb.getContent()).thenReturn(movies);

        List<Movie> moviesFetched = movieService.fetchMovies(pageRequest, null, null);

        int expectedInvocation = 1;

        verify(movieRepository, times(expectedInvocation)).findAll(ArgumentMatchers.eq(pageRequest));
        assert moviesFetched.size() == 2;
        assert moviesFetched.get(0).equals(movie1);
        assert moviesFetched.get(1).equals(movie2);
    }

    @Test
    public void fetchMovies_withFilter_movieRepositoryShouldFindMoviesWithGenreOrName() throws Exception {
        int page = 0;
        int size = 10;
        String genre = "action";
        String name = "matrix";

        Page<Movie> pageOjb = mock(Page.class);

        List<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie();

        movie1.setName("matrix 1");
        movie1.setGenre("action");
        movie1.setDuration(120);

        Movie movie2 = new Movie();

        movie2.setName("matrix 1");
        movie2.setGenre("action");
        movie2.setDuration(120);

        movies.add(movie1);
        movies.add(movie2);

        PageRequest pageRequest = new PageRequest(page, size);

        when(movieRepository.findMoviesByGenreContainingAndNameContaining(genre, name, pageRequest))
                .thenReturn(pageOjb);

        when(pageOjb.getContent()).thenReturn(movies);

        List<Movie> moviesFetched = movieService.fetchMovies(pageRequest, genre, name);

        int expectedInvocation = 1;


        verify(movieRepository, times(expectedInvocation)).findMoviesByGenreContainingAndNameContaining(
                ArgumentMatchers.eq(genre),
                ArgumentMatchers.eq(name),
                ArgumentMatchers.eq(pageRequest));

        assert moviesFetched.size() == 2;
        assert moviesFetched.get(0).equals(movie1);
        assert moviesFetched.get(1).equals(movie2);
    }

    @Test
    public void createMovie_shouldSaveMovie() throws Exception {

        String name = "sample";
        String genre = "sample";
        Integer duration = 1;
        String description = "sample";

        Movie movie = new Movie();
        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);

        when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);

        Movie movieCreated = movieService.createMovie(name, genre, duration, description);

        int expectedInvocation = 1;

        verify(movieRepository, times(expectedInvocation)).save(Mockito.any(Movie.class));

        assert movieCreated.getName() == name;
        assert movieCreated.getGenre() == genre;
        assert movieCreated.getDuration() == duration;
        assert movieCreated.getDescription() == description;
    }

    @Test
    public void fetchMovieById_noException_shouldFindOneMovie() throws Exception {
        Long idToFind = new Long(1);

        when(movieRepository.findOne(idToFind)).thenReturn(mock(Movie.class));

        movieService.fetchMovieById(idToFind);

        int expectedInvocations = 1;

        verify(movieRepository, times(expectedInvocations)).findOne(ArgumentMatchers.eq(idToFind));
    }

    @Test(expected = NotFoundException.class)
    public void fetchMovieById_notExists_shouldThrowException() throws Exception {
        Long idToFind = new Long(-1);

        movieService.fetchMovieById(idToFind);
    }

    @Test
    public void updateMovie() throws Exception {
        Movie movie = new Movie();

        movie.setName("sample");
        movie.setGenre("sample");
        movie.setDuration(120);

        when(movieRepository.findOne(movie.getId())).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie fetchedMovie = movieService.updateMovie(movie.getId(), movie.getName(), movie.getGenre(), movie.getDuration(),
                movie.getDescription());

        int expectedInvocation = 1;

        verify(movieRepository, times(expectedInvocation)).save(ArgumentMatchers.eq(movie));

        assert fetchedMovie.getName() == "sample";
        assert fetchedMovie.getGenre() == "sample";
        assert fetchedMovie.getDuration() == 120;
        assert fetchedMovie.getDescription() == null;
    }

    @Test
    public void deleteMovie_noException_shouldFindAndDeleteMovie() throws Exception {
        Long idToFind = new Long(1);

        Movie movie = new Movie();

        movie.setName("sample");
        movie.setGenre("sample");
        movie.setDuration(120);

        when(movieRepository.findOne(idToFind)).thenReturn(movie);

        movieService.deleteMovie(idToFind);

        int expectedInvocations = 1;

        verify(movieRepository, times(expectedInvocations)).findOne(ArgumentMatchers.eq(idToFind));
        verify(movieRepository, times(expectedInvocations)).delete(ArgumentMatchers.eq(movie));
    }
}