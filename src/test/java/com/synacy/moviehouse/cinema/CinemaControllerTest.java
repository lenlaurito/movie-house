package com.synacy.moviehouse.cinema;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.ArrayList;
import java.util.List;

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
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Pageable pageable = new PageRequest(0, 1, sort);

        List<Cinema> cinemas = new ArrayList<>();

        Cinema cinema1 = new Cinema();
        cinema1.setName("zzz");
        cinema1.setType("sample");

        Cinema cinema2 = new Cinema();
        cinema2.setName("aaa");
        cinema2.setType("sample");

        cinemas.add(cinema1);
        cinemas.add(cinema2);

        when(cinemaService.fetchAllCinema(pageable, null)).thenReturn(cinemas);

        List<Cinema> fetchedCinema = cinemaController.fetchAllCinema(pageable, null);

        verify(cinemaService, times(1)).fetchAllCinema(
                ArgumentMatchers.eq(pageable),
                ArgumentMatchers.eq(null)
        );

        assert fetchedCinema.size() == 2;
        assert fetchedCinema.get(0).equals(cinema1) == true;
        assert fetchedCinema.get(1).equals(cinema2) == true;
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