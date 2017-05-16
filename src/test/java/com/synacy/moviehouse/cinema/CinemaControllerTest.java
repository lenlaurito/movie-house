package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by banjoe on 5/15/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CinemaControllerTest {

    private CinemaController cinemaController;
    @Mock CinemaService cinemaService;

    @Before
    public void setUp() throws Exception {
        cinemaController = new CinemaController();
        cinemaController.setCinemaService(cinemaService);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchCinemaById_shouldThrowResourceNotFoundExceptionIfCinemaIsReturnNull() throws Exception {
        Long cinemaId = 200L;

        cinemaController.fetchCinemaById(cinemaId);
        verify(cinemaService, times(1)).fetchCinemaById(eq(cinemaId));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchAllCinema_shouldThrowResourceNotFoundExceptionWhenListReturnsEmpty() throws Exception {
        String type = "IMAX";

        cinemaController.fetchAllCinema(type);
        verify(cinemaService, times(1)).fetchAllCinema(eq(type));
    }

    @Test
    public void createCinema_shouldOnlyInvokeOnce() throws Exception {
        Cinema cinema = new Cinema();
        cinema.setName("Cinema 1");
        cinema.setType("3D");

       cinemaController.createCinema(cinema);
       verify(cinemaService, times(1)).saveNewCinema(cinema.getName(), cinema.getType());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCinema_shouldThrowResourceNotFoundExceptionIfCinemaIsReturnNull() throws Exception {
        Long cinemaId = 200L;

        cinemaController.updateCinema(cinemaId, new Cinema());
        verify(cinemaService, times(1)).fetchCinemaById(eq(cinemaId));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCinema_shouldThrowResourceNotFoundExceptionIfCinemaIsReturnNull() throws Exception {
        Long cinemaId = 200L;

        cinemaController.deleteCinema(cinemaId);
        verify(cinemaService, times(1)).fetchCinemaById(eq(cinemaId));
    }



}
