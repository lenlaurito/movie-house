package com.synacy.moviehouse.cinema;

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
    public void fetchById_cinemaNotFound_ResourceNotFoundException() {
        when(cinemaRepository.findOne(100L)).thenReturn(null);
        cinemaService.fetchById(100L);
    }

    @Test
    public void fetchAll_nonEmptyData_returnCollectionOfCinemas() throws Exception {
        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(mock(Cinema.class));
        cinemas.add(mock(Cinema.class));
        cinemas.add(mock(Cinema.class));
        when(cinemaRepository.findAll()).thenReturn(cinemas);

        List<Cinema> result = cinemaService.fetchAll();
        assertEquals(3, result.size());
    }

    @Test
    public void fetchAll_emptyData_returnEmptyCollectionOfCinemas() throws Exception {
        List<Cinema> cinemas = new ArrayList<>();
        when(cinemaRepository.findAll()).thenReturn(cinemas);

        List<Cinema> result = cinemaService.fetchAll();
        assertEquals(0, result.size());
    }

    @Test
    public void fetchAllByType() throws Exception {
        List<Cinema> cinemas = new ArrayList<>();
        when(cinemaRepository.findAllByType(CinemaType.STANDARD)).thenReturn(cinemas);

        List<Cinema> result = cinemaService.fetchAllByType(CinemaType.STANDARD.name());
        assertEquals(0, result.size());
    }

    @Test
    public void create() throws Exception {
        Cinema cinema = mock(Cinema.class);
        cinemaService.create(cinema);
        verify(cinemaRepository, times(1))
                .save(cinema);
    }

    @Test
    public void updateCinema() throws Exception {
        Cinema cinema = mock(Cinema.class);
        cinemaService.update(cinema, "Cinema 1", CinemaType.STANDARD);
        verify(cinemaRepository, times(1))
                .save(cinema);
    }

    @Test
    public void deleteCinema() throws Exception {
        Cinema cinema = mock(Cinema.class);
        cinemaService.delete(cinema);
        verify(cinemaRepository, times(1))
                .delete(cinema);
    }

}