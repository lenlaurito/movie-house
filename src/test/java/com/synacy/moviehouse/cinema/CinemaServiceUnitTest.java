package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.NoContentFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

//    @Test
//    public void fetchById_cinemaFound_shouldReturnCinema() throws Exception{
//        long id = 1L;
//        Cinema cinema = new Cinema();
//        when(cinemaRepository.findOne(id)).thenReturn(cinema);
//
//        cinemaService.fetchById(id);
//
//        verify(cinemaRepository,times(1)).findOne(id);
//        assertEquals(cinema, cinemaRepository.findOne(id));
//    }
//
//    @Test(expected = NoContentFoundException.class)
//    public void fetchById_noCinemaFound_shouldThrowNoContentFoundException() throws Exception{
//        long id = 1L;
//        when(cinemaRepository.findOne(id)).thenReturn(null);
//
//        cinemaService.fetchById(id);
//    }
//
//    @Test
//    public void fetchAll_cinemaFound_shouldReturnCinemaList() throws Exception{
//        List<Cinema> cinemaList = new ArrayList<>();
//        cinemaList.add(new Cinema());
//        cinemaList.add(new Cinema());
//        when(cinemaRepository.findAll()).thenReturn(cinemaList);
//
//        cinemaService.fetchAll();
//
//        assertEquals(cinemaList, cinemaRepository.findAll());
//    }
//
//    @Test(expected = NoContentFoundException.class)
//    public void fetchAll_noCinemaFound_shouldThrowNoContentFoundException() throws Exception{
//        List<Cinema> cinemaList = new ArrayList<>();
//        when(cinemaRepository.findAll()).thenReturn(cinemaList);
//
//        cinemaService.fetchAll();
//    }
//
//    @Test
//    public void fetchAllByType_cinemaFound_shouldReturnCinemaListByType() throws Exception{
//        List<Cinema> cinemaList = new ArrayList<>();
//        cinemaList.add(new Cinema());
//        cinemaList.add(new Cinema());
//        when(cinemaRepository.findAllByType(CinemaType.STANDARD)).thenReturn(cinemaList);
//
//        cinemaService.fetchAllByType(CinemaType.STANDARD);
//
//        assertEquals(cinemaList, cinemaRepository.findAllByType(CinemaType.STANDARD));
//    }
//
//    @Test(expected = NoContentFoundException.class)
//    public void fetchAllByType_noCinemaFound_shouldThrowNoContentFoundException() throws Exception{
//        List<Cinema> cinemaList = new ArrayList<>();
//        when(cinemaRepository.findAllByType(CinemaType.STANDARD)).thenReturn(cinemaList);
//
//        cinemaService.fetchAllByType(CinemaType.STANDARD);
//    }
//
//    @Test
//    public void createCinema_shouldCreateAndSaveCinema() throws Exception{
//        Cinema cinema = new Cinema();
//        when(cinemaRepository.save(cinema)).thenReturn(cinema);
//
//        cinemaService.createCinema("name",CinemaType.IMAX);
//
//        verify(cinemaRepository).save(cinema);
//        assertEquals(cinema, cinemaRepository.save(cinema));
//    }
//
//    @Test
//    public void updateCinema_shouldUpdateAndSaveCinema() throws Exception{
//        Cinema cinema = new Cinema();
//        when(cinemaRepository.save(cinema)).thenReturn(cinema);
//
//        cinemaService.updateCinema(cinema,"name",CinemaType.IMAX);
//
//        verify(cinemaRepository,times(1)).save(cinema);
//        assertEquals(cinema, cinemaRepository.save(cinema));
//    }
//
//    @Test
//    public void deleteCinema_shouldDeleteCinema() throws Exception{
//        Cinema cinema = new Cinema();
//
//        cinemaService.deleteCinema(cinema);
//
//        verify(cinemaRepository,times(1)).delete(cinema);
//    }


}
