package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.NoContentFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;

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
        long movieId = 1L;
        Movie expectedMovie = new Movie();
        expectedMovie.setName("name");
        expectedMovie.setGenre(MovieGenre.COMEDY);
        expectedMovie.setDuration(20);
        expectedMovie.setDescription("description");

        when(movieRepository.findOne(movieId)).thenReturn(expectedMovie);

        Movie actualMovie = movieService.fetchById(movieId);

        verify(movieRepository,times(1)).findOne(movieId);
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getName(), actualMovie.getName());
        assertEquals(expectedMovie.getGenre(), actualMovie.getGenre());
        assertEquals(expectedMovie.getDuration(), actualMovie.getDuration());
        assertEquals(expectedMovie.getDescription(), actualMovie.getDescription());
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchById_noMoviesFound_shouldReturnNoContentFoundException() throws Exception{
        long id = 1L;

        when(movieRepository.findOne(id)).thenReturn(null);

        movieService.fetchById(id);
    }

    @Test
    public void fetchAll_nameNotNullGenreIsNull_shouldReturnListOfMoviesByName() throws Exception{
        List<Movie> expectedMovieList = new ArrayList<>();
        expectedMovieList.add(new Movie());
        expectedMovieList.add(new Movie());

        when(movieRepository.findAllByName("name")).thenReturn(expectedMovieList);

        List<Movie> actualMovieList = movieService.fetchAll("name",null);

        verify(movieRepository, times(1)).findAllByName("name");
        assertEquals(expectedMovieList, actualMovieList);
        assertEquals(expectedMovieList.size(),actualMovieList.size());
    }

    @Test
    public void fetchAll_nameIsNullGenreNotNull_shouldReturnListOfMoviesByGenre() throws Exception{
        List<Movie> expectedMovieList = new ArrayList<>();
        expectedMovieList.add(new Movie());
        expectedMovieList.add(new Movie());

        when(movieRepository.findAllByGenre(MovieGenre.COMEDY)).thenReturn(expectedMovieList);

        List<Movie> actualMovieList = movieService.fetchAll(null,MovieGenre.COMEDY);

        verify(movieRepository, times(1)).findAllByGenre(MovieGenre.COMEDY);
        assertEquals(expectedMovieList, actualMovieList);
        assertEquals(expectedMovieList.size(), actualMovieList.size());
    }

    @Test
    public void fetchAll_nameNotNullGenreNotNull_shouldReturnListOfMoviesByNameAndGenre() throws Exception{
        List<Movie> expectedMovieList = new ArrayList<>();
        expectedMovieList.add(new Movie());
        expectedMovieList.add(new Movie());

        when(movieRepository.findAllByNameAndGenre("name",MovieGenre.COMEDY)).thenReturn(expectedMovieList);

        List<Movie> actualMovieList = movieService.fetchAll("name",MovieGenre.COMEDY);

        verify(movieRepository, times(1)).findAllByNameAndGenre("name",MovieGenre.COMEDY);
        assertEquals(expectedMovieList, actualMovieList);
        assertEquals(expectedMovieList.size(), actualMovieList.size());
    }

    @Test
    public void fetchAll_nameIsNullGenreIsNull_shouldReturnListOfAllMovies() throws Exception{
        List<Movie> expectedMovieList = new ArrayList<>();
        expectedMovieList.add(new Movie());
        expectedMovieList.add(new Movie());

        when(movieRepository.findAll()).thenReturn(expectedMovieList);

        List<Movie> actualMovieList = movieService.fetchAll(null,null);

        verify(movieRepository, times(1)).findAll();
        assertEquals(expectedMovieList, actualMovieList);
        assertEquals(expectedMovieList.size(), actualMovieList.size());
    }

    @Test (expected = NoContentFoundException.class)
    public void fetchAll_noMoviesFound_shouldReturnNoContentFoundException() throws Exception{
        List<Movie> movieList = new ArrayList<>();

        when(movieRepository.findAll()).thenReturn(movieList);

        movieService.fetchAll(null,null);
    }

    @Test
    public void fetchAllPaginated_nameNotNullGenreIsNull_shouldReturnPageOfMoviesByName() throws Exception{
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie());
        moviesList.add(new Movie());
        Page<Movie> expectedMoviePage = new PageImpl<>(moviesList, pageable, moviesList.size());

        when(movieRepository.findAllByName("name",new PageRequest(0,2))).thenReturn(expectedMoviePage);

        Page<Movie> actualMoviePage = movieService.fetchAllPaginated("name",null,0,2);

        verify(movieRepository, times(1)).findAllByName("name", new PageRequest(0,2));
        assertEquals(expectedMoviePage, actualMoviePage);
        assertEquals(expectedMoviePage.getTotalElements(), actualMoviePage.getTotalElements());
        assertEquals(expectedMoviePage.getTotalPages(), actualMoviePage.getTotalPages());
    }

    @Test
    public void fetchAllPaginated_nameIsNullGenreNotNull_shouldReturnPageOfMoviesByGenre() throws Exception{
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie());
        moviesList.add(new Movie());
        Page<Movie> expectedMoviePage = new PageImpl<>(moviesList, pageable, moviesList.size());

        when(movieRepository.findAllByGenre(MovieGenre.COMEDY,new PageRequest(0,2))).thenReturn(expectedMoviePage);

        Page<Movie> actualMoviePage = movieService.fetchAllPaginated(null,MovieGenre.COMEDY,0,2);

        verify(movieRepository, times(1)).findAllByGenre(MovieGenre.COMEDY, new PageRequest(0,2));
        assertEquals(expectedMoviePage,actualMoviePage);
        assertEquals(expectedMoviePage.getTotalElements(), actualMoviePage.getTotalElements());
        assertEquals(expectedMoviePage.getTotalPages(), actualMoviePage.getTotalPages());
    }

    @Test
    public void fetchAllPaginated_nameNotNullGenreNotNull_shouldReturnPageOfMoviesByNameAndGenre() throws Exception{
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie());
        moviesList.add(new Movie());
        Page<Movie> expectedMoviePage = new PageImpl<>(moviesList, pageable, moviesList.size());

        when(movieRepository.findAllByNameAndGenre("name",MovieGenre.COMEDY,new PageRequest(0,2))).thenReturn(expectedMoviePage);

        Page<Movie> actualMoviePage = movieService.fetchAllPaginated("name",MovieGenre.COMEDY,0,2);

        verify(movieRepository, times(1)).findAllByNameAndGenre("name",MovieGenre.COMEDY, new PageRequest(0,2));
        assertEquals(expectedMoviePage,actualMoviePage);
        assertEquals(expectedMoviePage.getTotalElements(), actualMoviePage.getTotalElements());
        assertEquals(expectedMoviePage.getTotalPages(), actualMoviePage.getTotalPages());
    }
//
    @Test
    public void fetchAllPaginated_nameIsNullGenreIsNull_shouldReturnPageOfAllMovies() throws Exception{
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie());
        moviesList.add(new Movie());
        Page<Movie> expectedMoviePage = new PageImpl<>(moviesList, pageable, moviesList.size());

        when(movieRepository.findAll(new PageRequest(0,2))).thenReturn(expectedMoviePage);

        Page<Movie> actualMoviePage = movieService.fetchAllPaginated(null,null,0,2);

        verify(movieRepository, times(1)).findAll(new PageRequest(0,2));
        assertEquals(expectedMoviePage,actualMoviePage);
        assertEquals(expectedMoviePage.getTotalElements(), actualMoviePage.getTotalElements());
        assertEquals(expectedMoviePage.getTotalPages(), actualMoviePage.getTotalPages());
    }

    @Test (expected = NoContentFoundException.class)
    public void fetchAllPaginated_noMoviesFound_shouldReturnNoContentFoundException() throws Exception{
        Page<Movie> page = mock(Page.class);

        when(movieRepository.findAll(new PageRequest(0, 2))).thenReturn(page);

        movieService.fetchAllPaginated(null,null, 0,2);
    }

    @Test
    public void createMovie_shouldCreateAndSaveMovie() throws Exception{
        Movie expectedMovie = new Movie();
        expectedMovie.setName("name");
        expectedMovie.setGenre(MovieGenre.COMEDY);
        expectedMovie.setDuration(20);
        expectedMovie.setDescription("description");

        when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        Movie actualMovie = movieService.createMovie("name",MovieGenre.COMEDY,20,"description");

        verify(movieRepository, times(1)).save(expectedMovie);
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getName(), actualMovie.getName());
        assertEquals(expectedMovie.getGenre(), actualMovie.getGenre());
        assertEquals(expectedMovie.getDuration(), actualMovie.getDuration());
        assertEquals(expectedMovie.getDescription(), actualMovie.getDescription());
    }

    @Test
    public void updateMovie_shouldUpdateAndSaveMovie() throws Exception{
        Long movieId = 1L;
        Movie expectedMovie = new Movie();
        expectedMovie.setName("name");
        expectedMovie.setGenre(MovieGenre.COMEDY);
        expectedMovie.setDuration(20);
        expectedMovie.setDescription("description");

        when(movieRepository.findOne(movieId)).thenReturn(expectedMovie);
        when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        Movie actualMovie = movieService.updateMovie(movieId,"name",MovieGenre.COMEDY,20,"description");

        verify(movieRepository,times(1)).save(expectedMovie);
        assertEquals(expectedMovie, actualMovie);
    }

    @Test
    public void deleteMovie_shouldDeleteMovie() throws Exception{
        Long movieId = 1L;
        Movie movie = new Movie();

        when(movieRepository.findOne(movieId)).thenReturn(movie);

        movieService.deleteMovie(movieId);

        verify(movieRepository,times(1)).delete(movie);
    }
}
