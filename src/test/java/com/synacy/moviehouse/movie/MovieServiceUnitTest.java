package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.NoContentFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by steven on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieServiceUnitTest {

    private MovieService movieService;

    @Mock MovieRepository movieRepository;

    @Before
    public void setup(){
        movieService = new MovieService();
        movieService.setMovieRepository(movieRepository);
    }

    @Test
    public void fetchById_movieFound_shouldReturnMovie() throws Exception{
        long id = 1L;
        Movie movie = new Movie();
        when(movieRepository.findOne(id)).thenReturn(movie);

        movieService.fetchById(id);

        verify(movieRepository,times(1)).findOne(id);
        assertEquals(movie, movieRepository.findOne(id));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchById_noMoviesFound_shouldReturnNoContentFoundException() throws Exception{
        long id = 1L;
        when(movieRepository.findOne(id)).thenReturn(null);

        movieService.fetchById(id);
    }

    @Test
    public void fetchAll_nameNotNullGenreIsNull_shouldReturnListOfMoviesByName() throws Exception{
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        when(movieRepository.findAllByName("name")).thenReturn(movieList);

        movieService.fetchAll("name",null);

        verify(movieRepository, times(1)).findAllByName("name");
        assertEquals(movieList, movieRepository.findAllByName("name"));
    }

    @Test
    public void fetchAll_nameIsNullGenreNotNull_shouldReturnListOfMoviesByGenre() throws Exception{
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        when(movieRepository.findAllByGenre(MovieGenre.COMEDY)).thenReturn(movieList);

        movieService.fetchAll(null,MovieGenre.COMEDY);

        verify(movieRepository, times(1)).findAllByGenre(MovieGenre.COMEDY);
        assertEquals(movieList, movieRepository.findAllByGenre(MovieGenre.COMEDY));
    }

    @Test
    public void fetchAll_nameNotNullGenreNotNull_shouldReturnListOfMoviesByNameAndGenre() throws Exception{
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        when(movieRepository.findAllByNameAndGenre("name",MovieGenre.COMEDY)).thenReturn(movieList);

        movieService.fetchAll("name",MovieGenre.COMEDY);

        verify(movieRepository, times(1)).findAllByNameAndGenre("name",MovieGenre.COMEDY);
        assertEquals(movieList, movieRepository.findAllByNameAndGenre("name", MovieGenre.COMEDY));
    }

    @Test
    public void fetchAll_nameIsNullGenreIsNull_shouldReturnListOfAllMovies() throws Exception{
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        when(movieRepository.findAll()).thenReturn(movieList);

        movieService.fetchAll(null,null);

        verify(movieRepository, times(1)).findAll();
        assertEquals(movieList, movieRepository.findAll());
    }

    @Test (expected = NoContentFoundException.class)
    public void fetchAll_noMoviesFound_shouldReturnNoContentFoundException() throws Exception{
        List<Movie> movieList = new ArrayList<>();
        when(movieRepository.findAll()).thenReturn(movieList);

        movieService.fetchAll(null,null);
    }

    @Test
    public void fetchAllPaginated_nameNotNullGenreIsNull_shouldReturnPageOfMoviesByName() throws Exception{
        Page<Movie> moviePage = mock(Page.class);
        when(movieRepository.findAllByName("name",new PageRequest(0,2))).thenReturn(moviePage);

        movieService.fetchAllPaginated("name",null,0,2);

        verify(movieRepository, times(1)).findAllByName("name", new PageRequest(0,2));
        assertEquals(moviePage, movieRepository.findAllByName("name",new PageRequest(0,2)));
    }

    @Test
    public void fetchAllPaginated_nameIsNullGenreNotNull_shouldReturnPageOfMoviesByGenre() throws Exception{
        Page<Movie> moviePage = mock(Page.class);
        when(movieRepository.findAllByGenre(MovieGenre.COMEDY,new PageRequest(0,2))).thenReturn(moviePage);

        movieService.fetchAllPaginated(null,MovieGenre.COMEDY,0,2);

        verify(movieRepository, times(1)).findAllByGenre(MovieGenre.COMEDY, new PageRequest(0,2));
        assertEquals(moviePage,movieRepository.findAllByGenre(MovieGenre.COMEDY,new PageRequest(0,2)));
    }

    @Test
    public void fetchAllPaginated_nameNotNullGenreNotNull_shouldReturnPageOfMoviesByNameAndGenre() throws Exception{
        Page<Movie> moviePage = mock(Page.class);
        when(movieRepository.findAllByNameAndGenre("name",MovieGenre.COMEDY,new PageRequest(0,2))).thenReturn(moviePage);

        movieService.fetchAllPaginated("name",MovieGenre.COMEDY,0,2);

        verify(movieRepository, times(1)).findAllByNameAndGenre("name",MovieGenre.COMEDY, new PageRequest(0,2));
        assertEquals(moviePage, movieRepository.findAllByNameAndGenre("name",MovieGenre.COMEDY,new PageRequest(0,2)));
    }

    @Test
    public void fetchAllPaginated_nameIsNullGenreIsNull_shouldReturnPageOfAllMovies() throws Exception{
        Page<Movie> moviePage = mock(Page.class);
        when(movieRepository.findAll(new PageRequest(0,2))).thenReturn(moviePage);

        movieService.fetchAllPaginated(null,null,0,2);

        verify(movieRepository, times(1)).findAll(new PageRequest(0,2));
        assertEquals(moviePage, movieRepository.findAll(new PageRequest(0,2)));
    }

    @Test
    public void createMovie_shouldCreateAndSaveMovie() throws Exception{
        movieService.createMovie("name",MovieGenre.COMEDY,20,"");
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    public void updateMovie_shouldUpdateAndSaveMovie() throws Exception{
        Movie movie = new Movie();
        movie.setName("name");
        movie.setGenre(MovieGenre.COMEDY);
        movie.setDuration(20);
        movie.setDescription("");
        when(movieRepository.save(movie)).thenReturn(movie);

        movieService.updateMovie(movie,"name",MovieGenre.COMEDY,20,"");

        verify(movieRepository,times(1)).save(movie);
        assertEquals(movie, movieRepository.save(movie));
    }

    @Test
    public void deleteMovie_shouldDeleteMovie() throws Exception{
        Movie movie = new Movie();
        movieService.deleteMovie(movie);
        verify(movieRepository,times(1)).delete(movie);
    }
}
