package com.synacy.moviehouse.cinema;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CinemaControllerTest {

    @Mock
    private CinemaService cinemaService;

    @InjectMocks
    CinemaController cinemaController;

    @Test
    public void fetchCinema_shouldGetCinema() throws Exception {
        cinemaController.fetchCinema(100L);
        verify(cinemaService, times(1))
                .fetchById(100L);
    }

    @Test
    public void fetchAllCinemas_passingNullType() throws Exception {
        List<Cinema> cinemas = mock(List.class);
        when(cinemaService.fetchAll()).thenReturn(cinemas);
        cinemaController.fetchAllCinemas(null);
        verify(cinemaService, times(1))
                .fetchAll();
    }

    @Test
    public void fetchAllCinemas_passingFilterByType() throws Exception {
        List<Cinema> cinemas = mock(List.class);
        when(cinemaService.fetchAllByType(CinemaType.STANDARD.name())).thenReturn(cinemas);
        cinemaController.fetchAllCinemas(CinemaType.STANDARD.name());
        verify(cinemaService, times(1))
                .fetchAllByType(CinemaType.STANDARD.name());
    }

    @Test
    public void createNewCinema() throws Exception {
        Cinema newCinema = mock(Cinema.class);
        cinemaController.createNewCinema(newCinema);
        verify(cinemaService, times(1))
                .create(newCinema);
    }

    @Test
    public void updateCinema() throws Exception {
        Cinema cinema = mock(Cinema.class);
        when(cinemaService.fetchById(100L)).thenReturn(cinema);
        when(cinema.getName()).thenReturn("Cinema");
        when(cinema.getType()).thenReturn(CinemaType.STANDARD);

        cinemaController.updateCinema(100L, cinema);

        verify(cinemaService, times(1))
                .update(cinema, "Cinema", CinemaType.STANDARD);
    }

    @Test
    public void deleteCinema() throws Exception {
        Cinema cinema = mock(Cinema.class);
        when(cinemaService.fetchById(100L)).thenReturn(cinema);
        cinemaController.deleteCinema(100L);
        verify(cinemaService, times(1))
                .delete(cinema);
    }

}