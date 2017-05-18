package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.IncompleteInformationException;
import com.synacy.moviehouse.exception.InvalidParameterException;
import com.synacy.moviehouse.exception.NoContentFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by steven on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieControllerUnitTest {

    @Autowired
    MovieController movieController;

    @Mock MovieService movieService;

    @Before
    public void setup(){
        movieController = new MovieController();
        movieController.setMovieService(movieService);
    }

    @Test
    public void fetchMovie_shouldReturnMovie() throws Exception{
        Long movieId = 1L;
        Movie expectedMovie = new Movie();
        expectedMovie.setName("name");
        expectedMovie.setGenre(MovieGenre.COMEDY);
        expectedMovie.setDuration(20);
        expectedMovie.setDescription("description");

        when(movieService.fetchById(movieId)).thenReturn(expectedMovie);

        Movie actualMovie = movieController.fetchMovie(movieId);

        verify(movieService, times(1)).fetchById(movieId);
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getName(), actualMovie.getName());
        assertEquals(expectedMovie.getGenre(), actualMovie.getGenre());
        assertEquals(expectedMovie.getDuration(), actualMovie.getDuration());
        assertEquals(expectedMovie.getDescription(), actualMovie.getDescription());
    }

    @Test
    public void fetchAllMovie_offsetAndMaxAreNull_shouldReturnListOfMovies() throws Exception{
        List<Movie> expectedMovieList = new ArrayList<>();
        expectedMovieList.add(new Movie());
        expectedMovieList.add(new Movie());

        when(movieService.fetchAll("name", MovieGenre.COMEDY)).thenReturn(expectedMovieList);

        ResponseEntity actualResponseEntity = movieController.fetchAllMovie("name",MovieGenre.COMEDY,null,null);

        verify(movieService, times(1)).fetchAll("name",MovieGenre.COMEDY);
        assertEquals(ResponseEntity.ok().body(expectedMovieList), actualResponseEntity);
    }

    @Test
    public void fetchAllMovie_offsetAndMaxAreNotNull_shouldReturnPageOfMoviesFound() throws Exception{
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie());
        moviesList.add(new Movie());
        Page<Movie> expectedMoviePage = new PageImpl<>(moviesList, pageable, moviesList.size());

        when(movieService.fetchAllPaginated("name",MovieGenre.COMEDY,0,2)).thenReturn(expectedMoviePage);

        ResponseEntity actualResponseEntity = movieController.fetchAllMovie("name",MovieGenre.COMEDY,0,2);

        verify(movieService, times(1)).fetchAllPaginated("name",MovieGenre.COMEDY,0,2);
        assertEquals(ResponseEntity.ok().body(expectedMoviePage), actualResponseEntity);
    }

    @Test(expected = InvalidParameterException.class)
    public void fetchAllMovie_offsetIsNullMaxNotNullViceVersa_shouldThrowInvalidParameterException() throws Exception{
        movieController.fetchAllMovie("name",MovieGenre.COMEDY,null,2);
    }

    @Test
    public void createMovie_nameGenreDurationAreValid_shouldCreateAndReturnMovie() throws Exception{
        Movie expectedMovie = new Movie();
        expectedMovie.setName("name");
        expectedMovie.setGenre(MovieGenre.COMEDY);
        expectedMovie.setDuration(20);
        expectedMovie.setDescription("description");

        when(movieService.createMovie("name",MovieGenre.COMEDY,20,"description")).thenReturn(expectedMovie);

        Movie actualMovie = movieController.createMovie(expectedMovie);

        verify(movieService, times(1)).createMovie(expectedMovie.getName(),expectedMovie.getGenre(),expectedMovie.getDuration(),expectedMovie.getDescription());
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getName(), actualMovie.getName());
        assertEquals(expectedMovie.getGenre(), actualMovie.getGenre());
        assertEquals(expectedMovie.getDuration(), actualMovie.getDuration());
        assertEquals(expectedMovie.getDescription(), actualMovie.getDescription());
    }

    @Test(expected = IncompleteInformationException.class)
    public void createMovie_eitherNameOrGenreOrDurationIsNull_shouldThrowIncompleteInformationException() throws Exception{
        Movie movie = new Movie();
        movieController.createMovie(movie);
    }

    @Test
    public void updateMovie_movieIdIsValid_shouldUpdateAndSaveMovie() throws Exception{
        Long movieId = 1L;
        Movie expectedMovie = new Movie();
        expectedMovie.setName("name");
        expectedMovie.setGenre(MovieGenre.COMEDY);
        expectedMovie.setDuration(20);
        expectedMovie.setDescription("description");

        when(movieService.updateMovie(movieId,"name",MovieGenre.COMEDY,20,"description")).thenReturn(expectedMovie);

        Movie actualMovie = movieController.updateMovie(movieId,expectedMovie);

        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getName(), actualMovie.getName());
        assertEquals(expectedMovie.getGenre(), actualMovie.getGenre());
        assertEquals(expectedMovie.getDuration(), actualMovie.getDuration());
        assertEquals(expectedMovie.getDescription(), actualMovie.getDescription());
    }

    @Test(expected = IncompleteInformationException.class)
    public void updateMovie_eitherNameOrGenreOrDurationIsNull_shouldThrowIncompleteInformationException() throws Exception{
        Long movieId = 1L;
        Movie movie = new Movie();
        movieController.updateMovie(movieId,movie);
    }

    @Test
    public void deleteMovie_shouldDeleteMovie() throws Exception{
        Long movieId = 1L;

        movieController.deleteMovie(movieId);

        verify(movieService, times(1)).deleteMovie(movieId);
    }
}
