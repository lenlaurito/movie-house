package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.InvalidRequestException;
import com.synacy.moviehouse.exception.ResourceNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CinemaServiceImplTest {

    @Mock
    private CinemaRepository cinemaRepository;

    @InjectMocks
    private CinemaServiceImpl cinemaService;

    @Test(expected = ResourceNotFoundException.class)
    public void fetchById_cinemaNotFound_shouldThrowResourceNotFoundException() {
        Long cinemaId = 100L;

        when(cinemaRepository.findOne(cinemaId)).thenReturn(null);

        cinemaService.fetchCinemaById(cinemaId);
    }

    @Test
    public void fetchAll_nonEmptyData_returnCollectionOfCinemas() throws Exception {
        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(mock(Cinema.class));
        cinemas.add(mock(Cinema.class));
        cinemas.add(mock(Cinema.class));
        when(cinemaRepository.findAll()).thenReturn(cinemas);

        List<Cinema> result = cinemaService.fetchAllCinemas(null);
        assertEquals(3, result.size());
    }

    @Test
    public void fetchAll_emptyData_returnEmptyCollectionOfCinemas() throws Exception {
        List<Cinema> cinemas = new ArrayList<>();
        when(cinemaRepository.findAll()).thenReturn(cinemas);

        List<Cinema> result = cinemaService.fetchAllCinemas(null);
        assertEquals(true, result.isEmpty());
    }

    @Test(expected = InvalidRequestException.class)
    public void fetchAllByType_shouldThrowInvalidRequestException() throws Exception {
        String notACinemaType = "SUB-STANDARD";

        cinemaService.fetchAllCinemas(notACinemaType);
    }

    @Test
    public void fetchAllByType_shouldRetrieveCinemasWithSpecifiedType() throws Exception {
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

        when(cinemaRepository.findAllByType(type)).thenReturn(cinemas);

        List<Cinema> resultCinemas = cinemaService.fetchAllCinemas(type.name());

        verify(cinemaRepository, times(0)).findAllByType(null);
        assertEquals("Cinema 1", resultCinemas.get(0).getName());
        assertEquals(type, resultCinemas.get(0).getType());
        assertEquals("Cinema 2", resultCinemas.get(1).getName());
        assertEquals(type, resultCinemas.get(1).getType());
    }

    @Test
    public void createCinema_shouldAssertWithNewlyCreatedCinemaWithSpecifiedDetails() throws Exception {
        String name = "Cinema X";
        CinemaType type = CinemaType.STANDARD;

        Cinema newCinema = new Cinema();
        newCinema.setName(name);
        newCinema.setType(type);

        when(cinemaRepository.save(newCinema)).thenReturn(newCinema);

        Cinema resultCinema = cinemaService.createCinema(name, type);

        verify(cinemaRepository, times(1)).save(newCinema);
        assertEquals(name, resultCinema.getName());
        assertEquals(type, resultCinema.getType());
    }

    @Test
    public void updateCinema_shouldUpdateAnExistingCinema() throws Exception {
        String name = "Cinema Z";
        CinemaType type = CinemaType.STANDARD;

        Cinema cinemaToUpdate = new Cinema();
        cinemaToUpdate.setName(name);
        cinemaToUpdate.setType(type);

        when(cinemaRepository.save(cinemaToUpdate)).thenReturn(cinemaToUpdate);

        Cinema resultCinema = cinemaService.updateCinema(cinemaToUpdate, name, type);

        verify(cinemaRepository, times(1)).save(cinemaToUpdate);
        assertEquals(name, resultCinema.getName());
        assertEquals(type, resultCinema.getType());
    }

    @Test
    public void deleteCinema_shouldDeleteAnExistingCinema() throws Exception {
        Cinema cinemaToDelete = new Cinema();
        cinemaToDelete.setName("Cinema Z");
        cinemaToDelete.setType(CinemaType.STANDARD);

        cinemaService.deleteCinema(cinemaToDelete);

        verify(cinemaRepository, times(1)).delete(cinemaToDelete);
    }

}