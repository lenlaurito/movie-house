package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.IncompleteInformationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

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
        cinemaController.fetchCinema(cinemaId);
        verify(cinemaService, times(1)).fetchById(cinemaId);
    }

    @Test
    public void fetchAllCinema_withNullCinemaType_shouldReturnListOfAllCinema() throws Exception{
        cinemaController.fetchAllCinema(null);
        verify(cinemaService, times(1)).fetchAll();
    }

    @Test
    public void fetchAllCinema_withValidCinemaType_shouldReturnListOfCinemaByType() throws Exception{
        cinemaController.fetchAllCinema(CinemaType.STANDARD);
        verify(cinemaService, times(1)).fetchAllByType(CinemaType.STANDARD);
    }

    @Test
    public void createCinema_shouldCreateAndReturnCinema() throws Exception{
        Cinema cinema = new Cinema();
        cinema.setName("name");
        cinema.setType(CinemaType.IMAX);

        cinemaController.createCinema(cinema);

        verify(cinemaService, times(1)).createCinema(cinema.getName(),cinema.getType());
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

        cinemaController.updateCinema(cinemaId,cinema);

        verify(cinemaService, times(1)).updateCinema(cinemaToBeUpdated,cinema.getName(),cinema.getType());
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
   }
}
