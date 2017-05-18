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
import org.springframework.data.domain.Page;

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

//    @Test
//    public void fetchMovie_shouldReturnMovie() throws Exception{
//        Movie movie = new Movie();
//        Long movieId = 1L;
//        when(movieService.fetchById(movieId)).thenReturn(movie);
//
//        movieController.fetchMovie(movieId);
//
//        verify(movieService, times(1)).fetchById(movieId);
//        assertEquals(movie,movieService.fetchById(movieId));
//    }
//
//    @Test
//    public void fetchAllMovie_offsetAndMaxAreNull_shouldReturnListOfMovies() throws Exception{
//        movieController.fetchAllMovie("name",MovieGenre.COMEDY,null,null);
//
//        verify(movieService, times(1)).fetchAll("name",MovieGenre.COMEDY);
//    }
//
//    @Test
//    public void fetchAllMovie_offsetAndMaxAreNotNull_shouldReturnPageOfMoviesFound() throws Exception{
//        Page<Movie> moviePage = mock(Page.class);
//        when(moviePage.getTotalPages()).thenReturn(1);
//        when(movieService.fetchAllPaginated("name",MovieGenre.COMEDY,0,2)).thenReturn(moviePage);
//
//        movieController.fetchAllMovie("name",MovieGenre.COMEDY,0,2);
//
//        verify(movieService, times(1)).fetchAllPaginated("name",MovieGenre.COMEDY,0,2);
//        assertEquals(moviePage, movieService.fetchAllPaginated("name",MovieGenre.COMEDY,0,2));
//    }
//
//    @Test(expected = NoContentFoundException.class)
//    public void fetchAllMovie_offsetAndMaxAreNotNullNoMoviesFound_shouldThrowNoContentFoundException() throws Exception{
//        Page<Movie> moviePage = mock(Page.class);
//        when(movieService.fetchAllPaginated("name",MovieGenre.COMEDY,0,2)).thenReturn(moviePage);
//
//        movieController.fetchAllMovie("name",MovieGenre.COMEDY,0,2);
//    }
//
//    @Test(expected = InvalidParameterException.class)
//    public void fetchAllMovie_offsetIsNullMaxNotNullViceVersa_shouldThrowInvalidParameterException() throws Exception{
//        movieController.fetchAllMovie("name",MovieGenre.COMEDY,null,2);
//    }
//
//    @Test
//    public void createMovie_nameGenreDurationAreValid_shouldCreateAndReturnMovie() throws Exception{
//        Movie movie = new Movie();
//        movie.setName("name");
//        movie.setGenre(MovieGenre.COMEDY);
//        movie.setDuration(20);
//        movie.setDescription("");
//        when(movieService.createMovie(movie.getName(),movie.getGenre(),movie.getDuration(),movie.getDescription())).thenReturn(movie);
//
//        movieController.createMovie(movie);
//
//        verify(movieService, times(1)).createMovie(movie.getName(),movie.getGenre(),movie.getDuration(),movie.getDescription());
//        assertEquals(movie, movieService.createMovie(movie.getName(),movie.getGenre(),movie.getDuration(),movie.getDescription()));
//    }
//
//    @Test(expected = IncompleteInformationException.class)
//    public void createMovie_eitherNameOrGenreOrDurationIsNull_shouldThrowIncompleteInformationException() throws Exception{
//        Movie movie = new Movie();
//        movie.setName(null);
//        movie.setGenre(null);
//        movie.setDuration(null);
//        movie.setDescription("");
//
//        movieController.createMovie(movie);
//    }
//
//    @Test
//    public void updateMovie_movieIdIsValid_shouldUpdateAndSaveMovie() throws Exception{
//        Long movieId = 1L;
//        Movie movie = new Movie();
//        movie.setName("name");
//        movie.setGenre(MovieGenre.COMEDY);
//        movie.setDuration(20);
//        movie.setDescription("");
//        Movie movieToBeUpdated = new Movie();
//        when(movieService.fetchById(movieId)).thenReturn(movieToBeUpdated);
//        when(movieService.updateMovie(movieToBeUpdated,movie.getName(),movie.getGenre(),movie.getDuration(),movie.getDescription())).thenReturn(movie);
//
//        movieController.updateMovie(movieId,movie);
//
//        verify(movieService, times(1)).updateMovie(movieToBeUpdated,movie.getName(),movie.getGenre(),movie.getDuration(),movie.getDescription());
//        assertEquals(movieToBeUpdated, movieService.fetchById(movieId));
//        assertEquals(movie, movieService.updateMovie(movieToBeUpdated,movie.getName(),movie.getGenre(),movie.getDuration(),movie.getDescription()));
//    }
//
//    @Test(expected = IncompleteInformationException.class)
//    public void updateMovie_eitherNameOrGenreOrDurationIsNull_shouldThrowIncompleteInformationException() throws Exception{
//        Long movieId = 1L;
//        Movie movie = new Movie();
//        movie.setName(null);
//        movie.setGenre(null);
//        movie.setDuration(null);
//        movie.setDescription("");
//
//        movieController.updateMovie(movieId,movie);
//    }
//
//    @Test
//    public void deleteMovie_shouldDeleteMovie() throws Exception{
//        Long movieId = 1L;
//        Movie movie = new Movie();
//        when(movieService.fetchById(movieId)).thenReturn(movie);
//
//        movieController.deleteMovie(movieId);
//
//        verify(movieService, times(1)).deleteMovie(movie);
//    }
}
