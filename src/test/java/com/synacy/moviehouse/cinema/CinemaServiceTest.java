package com.synacy.moviehouse.cinema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by banjoe on 5/15/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CinemaServiceTest {


    private CinemaService cinemaService;

    @Mock
    CinemaRepo cinemaRepo;

    @Before
    public void setUp(){
        cinemaService = new CinemaServiceImpl();
        cinemaService.setCinemaRepo(cinemaRepo);
    }

    @Test
    public void fetchAllCinema_shouldReturnAllCinemaWithNoSpecificFilter() throws Exception {
        Cinema cinema1 = new Cinema();
        Cinema cinema2 = new Cinema();

        List<Cinema> cinemaList = new ArrayList<>();
        cinemaList.add(cinema1);
        cinemaList.add(cinema2);

        when(cinemaRepo.findAll()).thenReturn(cinemaList);

        List<Cinema> resultList = cinemaService.fetchAllCinema(null);

        verify(cinemaRepo, times(1)).findAll();
        Assert.assertEquals(cinemaList, resultList);
    }

    @Test
    public void fetchAllCinema_shouldReturnAllCinemaWithTheType3D() throws Exception {
        Cinema cinema1 = new Cinema();
        cinema1.setType("3D");
        Cinema cinema2 = new Cinema();
        cinema2.setType("3D");

        List<Cinema> cinemaList = new ArrayList<>();
        cinemaList.add(cinema1);
        cinemaList.add(cinema2);

        when(cinemaRepo.findAllByType(eq("3D"))).thenReturn(cinemaList);

        List<Cinema> resultList = cinemaService.fetchAllCinema("3D");

        verify(cinemaRepo, times(1)).findAllByType(eq("3D"));
        Assert.assertEquals(cinemaList, resultList);
    }

    @Test
    public void saveNewCinema_shouldSaveNewCinema() throws Exception {
        Cinema cinema = new Cinema();
        cinema.setName("Test Cinema");
        cinema.setType("IMAX");

        when(cinemaRepo.save(eq(cinema))).thenReturn(cinema);

        Cinema result = cinemaService.saveNewCinema("Test Cinema", "IMAX");

        verify(cinemaRepo, times(1)).save(eq(new Cinema()));
        Assert.assertEquals(cinema, result);
    }

    @Test
    public void updateCinema_shouldUpdateTheExistingCinema() throws Exception {
        Cinema cinema = new Cinema();

        when(cinemaRepo.save(eq(cinema))).thenReturn(cinema);

        Cinema result = cinemaService.updateCinema(cinema,"Cinema 3", "IMAX");

        verify(cinemaRepo, times(1)).save(eq(cinema));
        Assert.assertEquals(cinema, result);
    }

}
