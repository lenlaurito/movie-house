package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.IncompleteInformationException;
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
        Cinema expectedCinema = new Cinema();

        when(cinemaService.fetchById(cinemaId)).thenReturn(expectedCinema);

        Cinema actualCinema = cinemaController.fetchCinema(cinemaId);

        verify(cinemaService, times(1)).fetchById(cinemaId);
        assertEquals(expectedCinema, actualCinema);
        assertEquals(expectedCinema.getName(), actualCinema.getName());
        assertEquals(expectedCinema.getType(), actualCinema.getType());
    }

    @Test
    public void fetchAllCinema_withNullCinemaType_shouldReturnListOfAllCinema() throws Exception{
        List<Cinema> expectedCinemaList = new ArrayList<>();
        expectedCinemaList.add(new Cinema());
        expectedCinemaList.add(new Cinema());

        when(cinemaService.fetchAll()).thenReturn(expectedCinemaList);

        List<Cinema> actualCinemaList = cinemaController.fetchAllCinema(null);

        verify(cinemaService, times(1)).fetchAll();
        assertEquals(expectedCinemaList, actualCinemaList);
        assertEquals(expectedCinemaList.size(), actualCinemaList.size());
    }

    @Test
    public void fetchAllCinema_withValidCinemaType_shouldReturnListOfCinemaByType() throws Exception{
        CinemaType cinemaType = CinemaType.STANDARD;
        List<Cinema> expectedCinemaList = new ArrayList<>();
        expectedCinemaList.add(new Cinema());
        expectedCinemaList.add(new Cinema());

        when(cinemaService.fetchAllByType(cinemaType)).thenReturn(expectedCinemaList);

        List<Cinema> actualCinemaList = cinemaController.fetchAllCinema(cinemaType);

        verify(cinemaService, times(1)).fetchAllByType(cinemaType);
        assertEquals(expectedCinemaList.size(), actualCinemaList.size());
    }

    @Test
    public void createCinema_shouldCreateAndReturnCinema() throws Exception{
        Cinema expectedCinema = new Cinema();
        expectedCinema.setName("name");
        expectedCinema.setType(CinemaType.IMAX);

        when(cinemaService.createCinema(expectedCinema.getName(),expectedCinema.getType())).thenReturn(expectedCinema);

        Cinema actualCinema = cinemaController.createCinema(expectedCinema);

        verify(cinemaService, times(1)).createCinema(expectedCinema.getName(),expectedCinema.getType());
        assertEquals(expectedCinema.getName(), actualCinema.getName());
        assertEquals(expectedCinema.getType(), actualCinema.getType());
    }

    @Test(expected = IncompleteInformationException.class)
    public void createCinema_withNullJsonInputs_shouldThrowIncompleteInformationException() throws Exception{
        Cinema cinema = new Cinema();
        cinemaController.createCinema(cinema);
    }

    @Test
    public void updateCinema_shouldUpdateAndReturnCinema() throws Exception{
        Long cinemaId = 1L;
        Cinema expectedCinema = new Cinema();
        expectedCinema.setName("name");
        expectedCinema.setType(CinemaType.IMAX);

        when(cinemaService.updateCinema(cinemaId,expectedCinema.getName(),expectedCinema.getType())).thenReturn(expectedCinema);

        Cinema actualCinema = cinemaController.updateCinema(cinemaId,expectedCinema);

        verify(cinemaService, times(1)).updateCinema(cinemaId,expectedCinema.getName(),expectedCinema.getType());
        assertEquals(expectedCinema.getId(), actualCinema.getId());
        assertEquals(expectedCinema.getName(), actualCinema.getName());
        assertEquals(expectedCinema.getType(), actualCinema.getType());
    }

    @Test(expected = IncompleteInformationException.class)
    public void updateCinema_withNullJsonInputs_shouldThrowIncompleteInformationException() throws Exception{
        Long cinemaId = 1L;
        Cinema cinema = new Cinema();

        cinemaController.updateCinema(cinemaId,cinema);
   }
}
