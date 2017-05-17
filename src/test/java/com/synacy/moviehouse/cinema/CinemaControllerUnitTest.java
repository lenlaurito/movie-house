package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.IncompleteInformationException;
import com.synacy.moviehouse.movie.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Created by steven on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CinemaControllerUnitTest {

    @Autowired
    CinemaController cinemaController;

    @Mock CinemaService cinemaService;

    @Before
    public void setup(){
        cinemaController = new CinemaController();
        cinemaController.setCinemaService(cinemaService);
    }

    @Test
    public void fetchCinema_shouldReturnCinema() throws Exception{
        long cinemaId = 1L;
        Cinema cinema = new Cinema();
        when(cinemaService.fetchById(cinemaId)).thenReturn(cinema);

        cinemaController.fetchCinema(cinemaId);

        verify(cinemaService, times(1)).fetchById(cinemaId);
        assertEquals(cinema, cinemaService.fetchById(cinemaId));
    }

    @Test
    public void fetchAllCinema_withNullCinemaType_shouldReturnListOfAllCinema() throws Exception{
        List<Cinema> cinemaList = new ArrayList<>();
        cinemaList.add(new Cinema());
        cinemaList.add(new Cinema());
        when(cinemaService.fetchAll()).thenReturn(cinemaList);

        cinemaController.fetchAllCinema(null);

        verify(cinemaService, times(1)).fetchAll();
        assertEquals(cinemaList, cinemaService.fetchAll());
    }

    @Test
    public void fetchAllCinema_withValidCinemaType_shouldReturnListOfCinemaByType() throws Exception{
        List<Cinema> cinemaList = new ArrayList<>();
        cinemaList.add(new Cinema());
        cinemaList.add(new Cinema());
        CinemaType cinemaType = CinemaType.STANDARD;
        when(cinemaService.fetchAllByType(cinemaType)).thenReturn(cinemaList);

        cinemaController.fetchAllCinema(cinemaType);

        verify(cinemaService, times(1)).fetchAllByType(cinemaType);
        assertEquals(cinemaList, cinemaService.fetchAllByType(cinemaType));
    }

    @Test
    public void createCinema_shouldCreateAndReturnCinema() throws Exception{
        Cinema cinema = new Cinema();
        cinema.setName("name");
        cinema.setType(CinemaType.IMAX);
        when(cinemaService.createCinema(cinema.getName(),cinema.getType())).thenReturn(cinema);

        cinemaController.createCinema(cinema);

        verify(cinemaService, times(1)).createCinema(cinema.getName(),cinema.getType());
        assertEquals(cinema, cinemaService.createCinema(cinema.getName(),cinema.getType()));
    }

    @Test(expected = IncompleteInformationException.class)
    public void createCinema_withNullJsonInputs_shouldThrowIncompleteInformationException() throws Exception{
        Cinema cinema = new Cinema();
        cinemaController.createCinema(cinema);
    }

    @Test
    public void createUpdate_shouldUpdateAndReturnCinema() throws Exception{
        Long cinemaId = 1L;
        Cinema cinema = new Cinema();
        cinema.setName("name");
        cinema.setType(CinemaType.IMAX);
        Cinema cinemaToBeUpdated = new Cinema();
        when(cinemaService.fetchById(cinemaId)).thenReturn(cinemaToBeUpdated);
        when(cinemaService.updateCinema(cinemaToBeUpdated,cinema.getName(),cinema.getType())).thenReturn(cinema);

        cinemaController.updateCinema(cinemaId,cinema);

        verify(cinemaService, times(1)).updateCinema(cinemaToBeUpdated,cinema.getName(),cinema.getType());
        assertEquals(cinemaToBeUpdated, cinemaService.fetchById(cinemaId));
        assertEquals(cinema, cinemaService.updateCinema(cinemaToBeUpdated,cinema.getName(),cinema.getType()));
    }

    @Test(expected = IncompleteInformationException.class)
    public void createUpdate_withNullJsonInputs_shouldThrowIncompleteInformationException() throws Exception{
        Long cinemaId = 1L;
        Cinema cinema = new Cinema();
        Cinema cinemaToBeUpdated = new Cinema();
        when(cinemaService.fetchById(cinemaId)).thenReturn(cinemaToBeUpdated);

        cinemaController.updateCinema(cinemaId,cinema);
   }

   @Test
    public void deleteCinema_shouldDeleteCinema() throws Exception{
       Long cinemaId = 1L;
       Cinema cinema = new Cinema();

       when(cinemaService.fetchById(cinemaId)).thenReturn(cinema);

       cinemaController.deleteCinema(cinemaId);

       verify(cinemaService, times(1)).deleteCinema(cinema);
       assertEquals(cinema, cinemaService.fetchById(cinemaId));
   }
}
