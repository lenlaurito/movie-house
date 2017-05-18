package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    public void fetchMovieById_movieFound_returnMovie() throws Exception {
        Movie movie = new Movie();
        movie.setName("Movie");
        movie.setGenre(Genre.ACTION);
        movie.setDuration(125);
        movie.setDescription("");
        when(movieRepository.findOne(100L)).thenReturn(movie);

        movieService.fetchMovieById(100L);

        assertNotNull(movie);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchMovieById_movieNotFound_shouldThrowResourceNotFoundException() throws Exception {
        Long movieId = 10L;

        movieService.fetchMovieById(movieId);
    }

    @Test
    public void fetchAllMovies_withNullParameters_returnCollectionOfMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.fetchAllMovies(null, null);

        verify(movieRepository, times(1)).findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void fetchAllMovies_withNullGenre_returnCollectionOfMovies() throws Exception {
        String movieName = "Sample Movie";
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        when(movieRepository.findAllByNameContainingIgnoreCase(movieName)).thenReturn(movies);

        List<Movie> result = movieService.fetchAllMovies(movieName, null);

        verify(movieRepository, times(1)).findAllByNameContainingIgnoreCase(movieName);
        assertEquals(3, result.size());
    }

    @Test
    public void fetchAllMovies_withNullName_returnCollectionOfMovies() throws Exception {
        Genre genre = Genre.ACTION;
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        when(movieRepository.findAllByGenre(genre)).thenReturn(movies);

        List<Movie> result = movieService.fetchAllMovies(null, genre.name());

        verify(movieRepository, times(1)).findAllByGenre(genre);
        assertEquals(3, result.size());
    }

    @Test
    public void fetchAllMovies_withSpecifiedParameters_returnCollectionOfMovies() throws Exception {
        String movieName = "Sample Movie";
        Genre genre = Genre.ACTION;
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        when(movieRepository.findAllByNameContainingIgnoreCaseAndGenre(movieName, genre)).thenReturn(movies);

        List<Movie> result = movieService.fetchAllMovies(movieName, genre.name());

        verify(movieRepository, times(1)).findAllByNameContainingIgnoreCaseAndGenre(movieName, genre);
        assertEquals(3, result.size());
    }

    @Test
    public void fetchAllMoviesWithPagination_shouldGetMoviesWithNullParameters() throws Exception {
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        Page<Movie> pagedMovies = new PageImpl<>(movies, pageable, movies.size());
        when(movieRepository.findAll(pageable)).thenReturn(pagedMovies);

        Page<Movie> response = movieService.fetchAllMoviesWithPagination(null, null, 0, 2);

        verify(movieRepository, times(1)).findAll(pageable);
        assertEquals(3, response.getTotalElements());
        assertEquals(2, response.getTotalPages());
    }

    @Test
    public void fetchAllMoviesWithPagination_shouldGetMoviesWithNullName() throws Exception {
        Genre genre = Genre.ACTION;
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        Page<Movie> pagedMovies = new PageImpl<>(movies, pageable, movies.size());
        when(movieRepository.findAllByGenre(genre, pageable)).thenReturn(pagedMovies);

        Page<Movie> response = movieService.fetchAllMoviesWithPagination(null, genre.name(), 0, 2);

        verify(movieRepository, times(1)).findAllByGenre(genre, pageable);
        assertEquals(3, response.getTotalElements());
        assertEquals(2, response.getTotalPages());
    }

    @Test
    public void fetchAllMoviesWithPagination_shouldGetMoviesWithNullGenre() throws Exception {
        String movieName = "Sample Movie";
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        Page<Movie> pagedMovies = new PageImpl<>(movies, pageable, movies.size());
        when(movieRepository.findAllByNameContainingIgnoreCase(movieName, pageable)).thenReturn(pagedMovies);

        Page<Movie> response = movieService.fetchAllMoviesWithPagination(movieName, null, 0, 2);

        verify(movieRepository, times(1)).findAllByNameContainingIgnoreCase(movieName, pageable);
        assertEquals(3, response.getTotalElements());
        assertEquals(2, response.getTotalPages());
    }

    @Test
    public void fetchAllMoviesWithPagination_shouldGetMoviesWithSpecifiedParameters() throws Exception {
        String movieName = "Sample Movie";
        Genre genre = Genre.ACTION;
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        Page<Movie> pagedMovies = new PageImpl<>(movies, pageable, movies.size());
        when(movieRepository.findAllByNameContainingIgnoreCaseAndGenre(movieName, genre, pageable)).thenReturn(pagedMovies);

        Page<Movie> response = movieService.fetchAllMoviesWithPagination(movieName, genre.name(), 0, 2);

        verify(movieRepository, times(1)).findAllByNameContainingIgnoreCaseAndGenre(movieName, genre, pageable);
        assertEquals(3, response.getTotalElements());
        assertEquals(2, response.getTotalPages());
    }

    @Test
    public void createMovie_shouldAssertWithNewlyCreatedMovieWithSpecifiedDetails() throws Exception {
        String name = "Movie X";
        Genre genre = Genre.ACTION;
        Integer duration = 160;
        String description = "Description";

        Movie newMovie = new Movie();
        newMovie.setName(name);
        newMovie.setGenre(genre);
        newMovie.setDuration(duration);
        newMovie.setDescription(description);

        movieService.createMovie(name, genre, duration, description);

        verify(movieRepository, times(1)).save(newMovie);
    }

    @Test
    public void updateMovie_shouldUpdateAnExistingMovie() throws Exception {
        Movie movie = new Movie();

        movieService.updateMovie(movie, "Movie 1", Genre.ACTION, 168, "Description");

        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void deleteMovie_shouldDeleteAnExistingMovie() throws Exception {
        Movie movie = new Movie();

        movieService.deleteMovie(movie);

        verify(movieRepository, times(1)).delete(movie);
    }

}