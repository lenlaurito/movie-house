package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.NoContentFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by steven on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CinemaServiceUnitTest {

    private CinemaService cinemaService;

    @Mock CinemaRepository cinemaRepository;

    @Before
    public void setup(){
        cinemaService = new CinemaService();
        cinemaService.setCinemaRepository(cinemaRepository);
    }

    @Test
    public void fetchById_cinemaFound_shouldReturnCinema() throws Exception{
        long id = 1L;
        Cinema cinema = new Cinema();
        when(cinemaRepository.findOne(id)).thenReturn(cinema);

        cinemaService.fetchById(id);

        verify(cinemaRepository,times(1)).findOne(id);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchById_noCinemaFound_shouldReturnNoContentFoundException() throws Exception{
        long id = 1L;

        when(cinemaRepository.findOne(id)).thenReturn(null);

        cinemaService.fetchById(id);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchAll_noCinemaFound_shouldReturnNoContentFoundException() throws Exception{
        List<Cinema> cinemaList = new ArrayList<>();

        when(cinemaRepository.findAll()).thenReturn(cinemaList);

        cinemaService.fetchAll();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchAllByType_noCinemaFound_shouldReturnNoContentFoundException() throws Exception{
        List<Cinema> cinemaList = new ArrayList<>();

        when(cinemaRepository.findAllByType(CinemaType.STANDARD)).thenReturn(cinemaList);

        cinemaService.fetchAllByType(CinemaType.STANDARD);
    }

    @Test
    public void createCinema_shouldCreateAndSaveCinema() throws Exception{
        cinemaService.createCinema("name",CinemaType.IMAX);

        verify(cinemaRepository).save(any(Cinema.class));
    }

    @Test
    public void updateCinema_shouldUpdateAndSaveCinema() throws Exception{
        Cinema cinema = new Cinema();
        cinema.setName("name");
        cinema.setType(CinemaType.IMAX);

        cinemaService.updateCinema(cinema,"name",CinemaType.IMAX);

        verify(cinemaRepository,times(1)).save(cinema);
    }

    @Test
    public void deleteCinema_shouldDeleteCinema() throws Exception{
        Cinema cinema = new Cinema()
                ;
        cinemaService.deleteCinema(cinema);

        verify(cinemaRepository,times(1)).delete(cinema);
    }


}
