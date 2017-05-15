package com.synacy.moviehouse.cinema;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.NoResultException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by michael on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CinemaServiceTest {

    @Autowired
    CinemaService cinemaService;

    @Mock CinemaRepository cinemaRepository;

    @Before
    public void setUp() {
        cinemaService = new CinemaService();

        cinemaService.setCinemaRepository(cinemaRepository);
    }

    @Test
    public void fetchAllCinema_withNoFilter_shouldFindAll() throws Exception {
        String type = null;

        PageRequest pageRequest = mock(PageRequest.class);
        Page<Cinema> page = mock(Page.class);
        List<Cinema> movies = mock(List.class);

        when(cinemaRepository.findAll(pageRequest)).thenReturn(page);
        when(page.getContent()).thenReturn(movies);

        cinemaService.fetchAllCinema(pageRequest, type);

        int expectedInvoccations = 1;

        verify(cinemaRepository, times(expectedInvoccations)).findAll(ArgumentMatchers.eq(pageRequest));
    }

    @Test
    public void fetchAllCinema_withFilter_shouldFindAllCinemaByTypeContaining() throws Exception {
        String type = "3d";

        PageRequest pageRequest = mock(PageRequest.class);
        Page<Cinema> page = mock(Page.class);
        List<Cinema> movies = mock(List.class);

        when(cinemaRepository.findAllCinemaByTypeContaining(type, pageRequest)).thenReturn(page);
        when(page.getContent()).thenReturn(movies);

        cinemaService.fetchAllCinema(pageRequest, type);

        int expectedInvoccations = 1;

        verify(cinemaRepository, times(expectedInvoccations)).findAllCinemaByTypeContaining(
                ArgumentMatchers.eq(type),
                ArgumentMatchers.eq(pageRequest));
    }

    @Test
    public void saveCinema_new_shouldSaveMovie() throws Exception {
        Cinema cinema = new Cinema();

        cinema.setName("sample");
        cinema.setType("sample");

        cinemaService.saveCinema(cinema);

        int expectedInvoccations = 1;

        verify(cinemaRepository, times(expectedInvoccations)).save(ArgumentMatchers.eq(cinema));
    }

    @Test
    public void saveCinema_existing_shouldSaveMovie() throws Exception {
        Cinema cinema = mock(Cinema.class);

        cinema.setName("sample");
        cinema.setType("sample");

        cinemaService.saveCinema(cinema);

        int expectedInvoccations = 1;

        verify(cinemaRepository, times(expectedInvoccations)).save(ArgumentMatchers.eq(cinema));
    }


    @Test
    public void fetchCinemaById_exist_shouldReturnCinema() throws Exception {
        Long idToFind = new Long(1);

        Cinema cinema = mock(Cinema.class);

        when(cinemaRepository.findOne(idToFind)).thenReturn(cinema);

        cinemaService.fetchCinemaById(idToFind);

        int expectedInvocations = 1;

        verify(cinemaRepository, times(expectedInvocations)).findOne(idToFind);
    }

    @Test(expected = NoResultException.class)
    public void fetchCinemaById_notExist_shouldThrowException() throws Exception {
        Long idToFind = new Long(1);

        cinemaService.fetchCinemaById(idToFind);
    }

    @Test
    public void deleteCinemaById_exist_shouldDeleteCinema() throws Exception {
        Long idToFind = new Long(1);

        Cinema cinema = mock(Cinema.class);

        when(cinemaRepository.findOne(idToFind)).thenReturn(cinema);

        cinemaService.deleteCinemaById(idToFind);

        int expectedInvocations = 1;

        verify(cinemaRepository, times(expectedInvocations)).findOne(ArgumentMatchers.eq(idToFind));
        verify(cinemaRepository, times(expectedInvocations)).delete(ArgumentMatchers.eq(cinema));
    }

    @Test(expected = NoResultException.class)
    public void deleteCinemaById_notExist_shouldThrowException() throws Exception {
        Long idToFind = new Long(1);

        cinemaService.fetchCinemaById(idToFind);
    }
}