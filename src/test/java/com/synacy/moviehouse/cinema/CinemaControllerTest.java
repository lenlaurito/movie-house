package com.synacy.moviehouse.cinema;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

        ResponseEntity<Cinema> response = cinemaController.fetchCinema(cinemaId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("Cinema", response.getBody().getName());
        assertEquals(CinemaType.STANDARD, response.getBody().getType());
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

        when(cinemaService.fetchAllCinemas(null)).thenReturn(cinemas);

        ResponseEntity<List<Cinema>> response = cinemaController.fetchAllCinemas(null);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("Cinema 1", response.getBody().get(0).getName());
        assertEquals(CinemaType.STANDARD, response.getBody().get(0).getType());
        assertEquals("Cinema 2", response.getBody().get(1).getName());
        assertEquals(CinemaType.SPECIAL, response.getBody().get(1).getType());
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

        when(cinemaService.fetchAllCinemas(type.name())).thenReturn(cinemas);

        ResponseEntity<List<Cinema>> response = cinemaController.fetchAllCinemas("STANDARD");

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("Cinema 1", response.getBody().get(0).getName());
        assertEquals(type, response.getBody().get(0).getType());
        assertEquals("Cinema 2", response.getBody().get(1).getName());
        assertEquals(type, response.getBody().get(1).getType());
    }

    @Test
    public void createNewCinema_shouldAssertWithNewlyCreatedCinemaWithSpecifiedDetails() throws Exception {
        String name = "Cinema X";
        CinemaType type = CinemaType.STANDARD;

        Cinema newCinema = new Cinema();
        newCinema.setName(name);
        newCinema.setType(type);

        when(cinemaService.createCinema(name, type)).thenReturn(newCinema);

        ResponseEntity<Cinema> response = cinemaController.createNewCinema(newCinema);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(name, response.getBody().getName());
        assertEquals(type, response.getBody().getType());
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

        ResponseEntity<Cinema> response = cinemaController.updateCinema(cinemaId, cinemaToUpdate);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(name, response.getBody().getName());
        assertEquals(type, response.getBody().getType());
    }

    @Test
    public void deleteCinema_shouldDeleteAnExistingCinema() throws Exception {
        Long cinemaId = 100L;

        Cinema cinemaToDelete = new Cinema();
        cinemaToDelete.setName("Cinema Z");
        cinemaToDelete.setType(CinemaType.STANDARD);

        when(cinemaService.fetchCinemaById(cinemaId)).thenReturn(cinemaToDelete);

        ResponseEntity<Cinema> response = cinemaController.deleteCinema(cinemaId);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
    }

}