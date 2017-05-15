package com.synacy.moviehouse.cinema;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
public class CinemaServiceIntegrationTest {

    @Autowired
    CinemaService cinemaService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @After
    public void cleanup() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "cinema");
    }

    @Test
    @Sql(statements = "insert into cinema values (100000, 'sample 1', 'type 1')")
    @Sql(statements = "insert into cinema values (100001, 'sample 2', 'type 1')")
    @Sql(statements = "insert into cinema values (100002, 'sample 3', 'type 2')")
    @Sql(statements = "insert into cinema values (100003, 'sample 4', 'type 2')")
    public void fetchAllCinema_withoutFilter_shouldReturnAllCollectionOfCinema() throws Exception {
        int page = 0;
        int size = 10;
        String type = null;

        PageRequest pageRequest = new PageRequest(page, size);

        Iterable<Cinema> cinemas = cinemaService.fetchAllCinema(pageRequest, type);

        int expectedSize = 4;
        assertEquals(expectedSize, cinemas.spliterator().getExactSizeIfKnown());
    }

    @Test
    @Sql(statements = "insert into cinema values (100000, 'sample 1', 'type 1')")
    @Sql(statements = "insert into cinema values (100001, 'sample 3', 'type 2')")
    @Sql(statements = "insert into cinema values (100002, 'sample 4', 'type 2')")
    public void fetchAllCinema_withFilter_shouldReturnAllCollectionOfCinemaOfSameType() throws Exception {
        int page = 0;
        int size = 10;
        String type = "type 2";

        PageRequest pageRequest = new PageRequest(page, size);

        Iterable<Cinema> cinemas = cinemaService.fetchAllCinema(pageRequest, type);

        int expectedSize = 2;
        assertEquals(expectedSize, cinemas.spliterator().getExactSizeIfKnown());
    }

    @Test
    @Commit
    public void saveCinema_new_cinemaIsSaved() throws Exception {
        Cinema cinema = new Cinema();

        cinema.setName("cinema a");
        cinema.setType("imax");

        Cinema savedCinema = cinemaService.saveCinema(cinema);

        TestTransaction.end();

        int expectedSize = 1;
        String condition = "id='" + savedCinema.getId() + "'";

        assertEquals(expectedSize, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "cinema", condition));
    }

    @Test
    @Sql(statements = "insert into cinema values (100000, 'sample 1', 'type 1')")
    @Commit
    public void saveCinema_existing_cinemaIsSaved() throws Exception {
        Long idToFind = new Long(100000);

        Cinema cinema = cinemaService.fetchCinemaById(idToFind);

        cinema.setType("imax");

        Cinema savedCinema = cinemaService.saveCinema(cinema);

        TestTransaction.end();

        int expectedSize = 1;

        String condition = "id='" + savedCinema.getId() + "' and type = '" + cinema.getType() + "'";

        assertEquals(expectedSize, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "cinema", condition));
    }

    @Test
    @Sql(statements = "insert into cinema values (100000, 'sample 1', 'type 1')")
    public void fetchCinemaById_existing_shouldReturnCinema() throws Exception {
        Long idToFind = new Long(100000);

        Cinema cinema = cinemaService.fetchCinemaById(idToFind);

        assertNotNull(cinema);
        assertEquals(idToFind, cinema.getId());
    }

    @Test(expected = NoResultException.class)
    public void fetchCinemaById_notExisting_shouldThrowException() throws Exception {
        Long idToFind = new Long(100000);

        Cinema cinema = cinemaService.fetchCinemaById(idToFind);
    }

    @Test
    @Sql(statements = "insert into cinema values (100000, 'sample 1', 'type 1')")
    @Commit
    public void deleteCinemaById() throws Exception {
        Long idToFind = new Long(100000);

        cinemaService.deleteCinemaById(idToFind);
        TestTransaction.end();

        int expectedSize = 0;

        String condition = "id='" + idToFind.toString() + "'";

        assertEquals(expectedSize, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "cinema", condition));
    }

    @Test(expected = NoResultException.class)
    public void deleteCinemaById_notExisting_shouldThrowException() throws Exception {
        Long idToFind = new Long(100000);

        cinemaService.deleteCinemaById(idToFind);
    }
}