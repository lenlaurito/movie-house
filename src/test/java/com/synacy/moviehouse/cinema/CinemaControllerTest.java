package com.synacy.moviehouse.cinema;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by michael on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CinemaControllerTest {

    CinemaController cinemaController;

    @Mock
    CinemaService cinemaService;

    @Before
    public void setUp() throws Exception {
        cinemaController = new CinemaController();

        cinemaController.cinemaService = cinemaService;
    }

    @Test
    public void fetchAllCinema() throws Exception {
        Pageable pageable = mock(Pageable.class);

        cinemaController.fetchAllCinema(pageable, null);

        verify(cinemaService, times(1)).fetchAllCinema(
                ArgumentMatchers.eq(pageable),
                ArgumentMatchers.eq(null)
        );
    }

    @Test
    public void createCinema() throws Exception {
        Cinema cinema = mock(Cinema.class);

        String name = "sample";
        String type = "sample";

        when(cinema.getName()).thenReturn(name);
        when(cinema.getType()).thenReturn(type);

        cinemaController.createCinema(cinema);

        verify(cinemaService, times(1)).createCinema(
                ArgumentMatchers.eq(name),
                ArgumentMatchers.eq(type)
        );
    }

    @Test
    public void fetchCinemaById() throws Exception {
        Long idTofind = new Long(1);
        Cinema cinema = mock(Cinema.class);

        when(cinemaService.fetchCinemaById(idTofind)).thenReturn(cinema);

        cinemaController.fetchCinemaById(idTofind);

        verify(cinemaService, times(1)).fetchCinemaById(ArgumentMatchers.eq(idTofind));
    }

    @Test
    public void updateCinema() throws Exception {
        Long idToFind = new Long(1);

        Cinema cinema = mock(Cinema.class);
        Cinema cinemaRequest = mock(Cinema.class);

        String name = "sample";
        String type = "sample";

        when(cinemaRequest.getName()).thenReturn(name);
        when(cinemaRequest.getType()).thenReturn(type);

        cinemaController.updateCinema(idToFind, cinemaRequest);

        verify(cinemaService, times(1)).updateCinema(
                ArgumentMatchers.eq(idToFind),
                ArgumentMatchers.eq(name),
                ArgumentMatchers.eq(type)
        );
    }

    @Test
    public void deleteCinema() throws Exception {
        Long idTofind = new Long(1);

        cinemaController.deleteCinema(idTofind);

        verify(cinemaService, times(1)).deleteCinemaById(ArgumentMatchers.eq(idTofind));
    }

}