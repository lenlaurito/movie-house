package com.synacy.moviehouse.cinema;

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
        cinemaService.fetchAllCinema(null);
        verify(cinemaRepo, times(1)).findAll();
    }

    @Test
    public void fetchAllCinema_shouldReturnAllCinemaWithTheType3D() throws Exception {
        cinemaService.fetchAllCinema("3D");
        verify(cinemaRepo, times(1)).findAllByType(eq("3D"));
    }

    @Test
    public void saveNewCinema_shouldSaveNewCinema() throws Exception {
        cinemaService.saveNewCinema("Test Cinema", "IMAX");
        verify(cinemaRepo, times(1)).save(eq(new Cinema()));
    }

    @Test
    public void updateCinema_shouldUpdateTheExistingCinema() throws Exception {
        Cinema cinema = new Cinema();

        cinemaService.updateCinema(cinema,"Cinema 3", "IMAX");
        verify(cinemaRepo, times(1)).save(eq(cinema));
    }

}
