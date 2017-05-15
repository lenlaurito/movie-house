package com.synacy.moviehouse.movie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

import static org.junit.Assert.*;

/**
 * Created by michael on 5/15/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class MovieServiceIntegreationTest {

    @Autowired
    MovieService movieService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String tableName = "movie";

    @After
    public void cleanup() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, tableName);
    }

    @Test
    @Sql(statements = "insert into movie values (100000, null, 120, 'sample genre 1', 'sample name 0')")
    @Sql(statements = "insert into movie values (100001, null, 120, 'sample genre 1', 'sample name 1')")
    @Sql(statements = "insert into movie values (100002, null, 120, 'sample genre 2', 'sample name 2')")
    @Sql(statements = "insert into movie values (100003, null, 120, 'sample genre 3', 'sample name 3')")
    public void fetchMovies_withNoFilter_shouldReturnFilteredCollectionOfMovies() throws Exception {
        int page = 0;
        int size = 10;

        PageRequest pageRequest = new PageRequest(page, size);

        Iterable<Movie> movies = movieService.fetchMovies(pageRequest, null, null);

        int expectedSize = 4;
        assertEquals(expectedSize, movies.spliterator().getExactSizeIfKnown());
    }

    @Test
    @Sql(statements = "insert into movie values (100000, null, 120, 'sample genre 1', 'sample name 0')")
    @Sql(statements = "insert into movie values (100001, null, 120, 'sample genre 1', 'sample name 1')")
    @Sql(statements = "insert into movie values (100002, null, 120, 'sample genre 2', 'sample name 2')")
    @Sql(statements = "insert into movie values (100003, null, 120, 'sample genre 3', 'sample name 3')")
    public void fetchMovies_withFilter_shouldReturnFilteredCollectionOfMovies() throws Exception {
        int page = 0;
        int size = 10;

        String genre = "sample genre 1";
        String name  = "sample name";

        PageRequest pageRequest = new PageRequest(page, size);

        Iterable<Movie> movies = movieService.fetchMovies(pageRequest, genre, name);

        int expectedSize = 2;
        assertEquals(expectedSize, movies.spliterator().getExactSizeIfKnown());
    }

    @Test
    @Commit
    public void createMovie_movieIsSaved() throws Exception {
        Movie movie = new Movie();

        movie.setName("matrix");
        movie.setDuration(120);
        movie.setGenre("action");

        Movie savedMovie = movieService.createMovie(movie);
        TestTransaction.end();

        int expectedSize = 1;
        String condition = "id='" + savedMovie.getId() + "'";

        assertEquals(expectedSize, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, tableName, condition));
    }

    @Test
    @Sql(statements = "insert into movie values (100000, null, 120, 'sample genre 1', 'sample name 0')")
    public void fetchMovieById_movieFound_shouldReturnMovie() throws Exception {
        Long idToFind = new Long(100000);

        Movie movie = movieService.fetchMovieById(idToFind);

        assertNotNull(movie);
    }

    @Test
    @Sql(statements = "insert into movie values (100000, null, 120, 'sample genre 1', 'sample name 0')")
    @Commit
    public void updateMovie_movieIsSaved() throws Exception {
        Long idToFind = new Long(100000);

        Movie movie = movieService.fetchMovieById(idToFind);

        movie.setName("sample name 123");

        Movie savedMovie = movieService.updateMovie(movie);
        TestTransaction.end();

        int expectedSize = 1;
        String condition = "id='" + savedMovie.getId() + "' and name = '" + movie.getName() + "'";

        assertEquals(expectedSize, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, tableName, condition));
    }

    @Test(expected = NoResultException.class)
    public void deleteMovie_movieFound_movieIsDeleted() throws Exception {
        Long idToFind = new Long(-1);
        movieService.deleteMovie(idToFind);
    }
}