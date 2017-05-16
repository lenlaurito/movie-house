package com.synacy.moviehouse.cinema;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CinemaControllerTest {

    @Mock
    private CinemaService cinemaService;

    @InjectMocks
    CinemaController cinemaController;

    @Test
    public void fetchCinema_shouldGetACorrectCinema() throws Exception {
        Long cinemaId = 2L;
        Cinema cinema = new Cinema();
        cinema.setName("Cinema");
        cinema.setType(CinemaType.STANDARD);

        when(cinemaService.fetchCinemaById(cinemaId)).thenReturn(cinema);

        cinemaController.fetchCinema(cinemaId);

        verify(cinemaService, times(1)).fetchCinemaById(cinemaId);
        assertEquals("Cinema", cinema.getName());
        assertEquals(CinemaType.STANDARD, cinema.getType());
    }

    @Test
    public void fetchAllCinemas_shouldRetrieveCinemasWithUnspecifiedType() throws Exception {
        Cinema cinema1 = new Cinema();
        cinema1.setName("Cinema 1");
        cinema1.setType(CinemaType.STANDARD);

        Cinema cinema2 = new Cinema();
        cinema2.setName("Cinema 2");
        cinema2.setType(CinemaType.SPECIAL);

        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(cinema1);
        cinemas.add(cinema2);

        when(cinemaService.fetchAllCinemas()).thenReturn(cinemas);

        List<Cinema> resultCinemas = cinemaController.fetchAllCinemas(null);

        verify(cinemaService, times(1)).fetchAllCinemas();
        verify(cinemaService, times(0)).fetchAllCinemasByType(null);
        assertEquals("Cinema 1", resultCinemas.get(0).getName());
        assertEquals(CinemaType.STANDARD, resultCinemas.get(0).getType());
        assertEquals("Cinema 2", resultCinemas.get(1).getName());
        assertEquals(CinemaType.SPECIAL, resultCinemas.get(1).getType());
    }

    @Test
    public void fetchAllCinemas_shouldRetrieveCinemasWithSpecifiedType() throws Exception {
        CinemaType type = CinemaType.STANDARD;

        Cinema cinema1 = new Cinema();
        cinema1.setName("Cinema 1");
        cinema1.setType(type);

        Cinema cinema2 = new Cinema();
        cinema2.setName("Cinema 2");
        cinema2.setType(type);

        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(cinema1);
        cinemas.add(cinema2);

        when(cinemaService.fetchAllCinemasByType(type.name())).thenReturn(cinemas);

        List<Cinema> resultCinemas = cinemaController.fetchAllCinemas("STANDARD");

        verify(cinemaService, times(0)).fetchAllCinemas();
        verify(cinemaService, times(1)).fetchAllCinemasByType(type.name());
        assertEquals("Cinema 1", resultCinemas.get(0).getName());
        assertEquals(type, resultCinemas.get(0).getType());
        assertEquals("Cinema 2", resultCinemas.get(1).getName());
        assertEquals(type, resultCinemas.get(1).getType());
    }

    @Test
    public void createNewCinema_shouldAssertWithNewlyCreatedCinemaWithSpecifiedDetails() throws Exception {
        String name = "Cinema X";
        CinemaType type = CinemaType.STANDARD;

        Cinema newCinema = new Cinema();
        newCinema.setName(name);
        newCinema.setType(type);

        when(cinemaService.createCinema(name, type)).thenReturn(newCinema);

        Cinema resultCinema = cinemaController.createNewCinema(newCinema);

        verify(cinemaService, times(1)).createCinema(name, type);
        assertEquals(name, resultCinema.getName());
        assertEquals(type, resultCinema.getType());
    }

    @Test
    public void updateCinema_shouldUpdateAnExistingCinema() throws Exception {
        Long cinemaId = 2L;
        String name = "Cinema Z";
        CinemaType type = CinemaType.STANDARD;

        Cinema cinemaToUpdate = new Cinema();
        cinemaToUpdate.setName(name);
        cinemaToUpdate.setType(type);

        when(cinemaService.fetchCinemaById(cinemaId)).thenReturn(cinemaToUpdate);
        when(cinemaService.updateCinema(cinemaToUpdate, name, type)).thenReturn(cinemaToUpdate);

        Cinema resultCinema = cinemaController.updateCinema(cinemaId, cinemaToUpdate);

        verify(cinemaService, times(1)).updateCinema(cinemaToUpdate, name, type);
        assertEquals(name, resultCinema.getName());
        assertEquals(type, resultCinema.getType());
    }

    @Test
    public void deleteCinema_shouldDeleteAnExistingCinema() throws Exception {
        Long cinemaId = 100L;

        Cinema cinemaToDelete = new Cinema();
        cinemaToDelete.setName("Cinema Z");
        cinemaToDelete.setType(CinemaType.STANDARD);

        when(cinemaService.fetchCinemaById(cinemaId)).thenReturn(cinemaToDelete);

        cinemaController.deleteCinema(cinemaId);

        verify(cinemaService, times(1)).deleteCinema(cinemaToDelete);
    }

}