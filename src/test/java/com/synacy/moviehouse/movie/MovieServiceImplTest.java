package com.synacy.moviehouse.movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    public void fetchById_movieFound_returnMovie() throws Exception {
        Movie movie = new Movie();
        movie.setName("Movie");
        movie.setGenre(Genre.ACTION);
        movie.setDuration(125);
        movie.setDescription("");
        when(movieRepository.findOne(100L)).thenReturn(movie);
        movieService.fetchById(100L);
    }

    @Test
    public void fetchAll_nonEmptyData_returnCollectionOfMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        movies.add(mock(Movie.class));
        movies.add(mock(Movie.class));
        movies.add(mock(Movie.class));
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.fetchAll();
        assertEquals(3, result.size());
    }

    @Test
    public void fetchAllByNameAndGenre() throws Exception {
        List<Movie> movies = new ArrayList<>();
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.fetchAll();
        assertEquals(0, result.size());
    }

    @Test
    public void fetchPaginatedMovies() throws Exception {
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Movie> moviesList = new ArrayList<>();
        moviesList.add(mock(Movie.class));
        moviesList.add(mock(Movie.class));
        moviesList.add(mock(Movie.class));

        Page<Movie> movies = new PageImpl<>(moviesList, pageable, moviesList.size());
        when(movieRepository.findAll(pageable)).thenReturn(movies);

        Page<Movie> response = movieService.fetchAllWithPagination(null, null, 0, 2);
        assertEquals(3, response.getTotalElements());
    }

    @Test
    public void fetchAllByGenre() throws Exception {
        List<Movie> movies = new ArrayList<>();
        when(movieRepository.findAllByGenre(Genre.ACTION)).thenReturn(movies);

        List<Movie> result = movieService.fetchAllByGenre(Genre.ACTION.name());
        assertEquals(0, result.size());
    }

    @Test
    public void create() throws Exception {
        Movie movie = mock(Movie.class);
        movieService.create(movie);
        verify(movieRepository, times(1))
                .save(movie);
    }

    @Test
    public void update() throws Exception {
        Movie movie = mock(Movie.class);
        movieService.update(movie, "Movie 1", Genre.ACTION, 168, "Description");
        verify(movieRepository, times(1))
                .save(movie);
    }

    @Test
    public void delete() throws Exception {
        Movie movie = mock(Movie.class);
        movieService.delete(movie);
        verify(movieRepository, times(1))
                .delete(movie);
    }

}