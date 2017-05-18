package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.NoContentFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Test
    public void fetchById_cinemaFound_shouldReturnCinema() throws Exception{
        long id = 1L;
        Cinema expectedCinema = new Cinema();
        expectedCinema.setName("Name");
        expectedCinema.setType(CinemaType.STANDARD);

        when(cinemaRepository.findOne(id)).thenReturn(expectedCinema);

        Cinema actualCinema = cinemaService.fetchById(id);

        verify(cinemaRepository,times(1)).findOne(id);
        assertEquals(expectedCinema.getName(), actualCinema.getName());
        assertEquals(expectedCinema.getType(), actualCinema.getType());
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchById_noCinemaFound_shouldThrowNoContentFoundException() throws Exception{
        long id = 1L;

        when(cinemaRepository.findOne(id)).thenReturn(null);

        cinemaService.fetchById(id);
    }

    @Test
    public void fetchAll_cinemaFound_shouldReturnCinemaList() throws Exception{
        List<Cinema> expectedCinemaList = new ArrayList<>();
        expectedCinemaList.add(new Cinema());
        expectedCinemaList.add(new Cinema());

        when(cinemaRepository.findAll()).thenReturn(expectedCinemaList);

        List<Cinema> actualCinemaList = cinemaService.fetchAll();

        verify(cinemaRepository,times(1)).findAll();
        assertEquals(expectedCinemaList.size(), actualCinemaList.size());
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchAll_noCinemaFound_shouldThrowNoContentFoundException() throws Exception{
        List<Cinema> cinemaList = new ArrayList<>();

        when(cinemaRepository.findAll()).thenReturn(cinemaList);

        cinemaService.fetchAll();
    }

    @Test
    public void fetchAllByType_cinemaFound_shouldReturnCinemaListByType() throws Exception{
        List<Cinema> expectedCinemaList = new ArrayList<>();
        expectedCinemaList.add(new Cinema());
        expectedCinemaList.add(new Cinema());

        when(cinemaRepository.findAllByType(CinemaType.STANDARD)).thenReturn(expectedCinemaList);

        List<Cinema> actualCinemaList = cinemaService.fetchAllByType(CinemaType.STANDARD);

        verify(cinemaRepository,times(1)).findAllByType(CinemaType.STANDARD);
        assertEquals(expectedCinemaList.size(), actualCinemaList.size());
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchAllByType_noCinemaFound_shouldThrowNoContentFoundException() throws Exception{
        List<Cinema> cinemaList = new ArrayList<>();

        when(cinemaRepository.findAllByType(CinemaType.STANDARD)).thenReturn(cinemaList);

        cinemaService.fetchAllByType(CinemaType.STANDARD);
    }

    @Test
    public void createCinema_shouldCreateAndSaveCinema() throws Exception{
        Cinema expectedCinema = new Cinema();
        expectedCinema.setName("Name");
        expectedCinema.setType(CinemaType.STANDARD);

        when(cinemaRepository.save(expectedCinema)).thenReturn(expectedCinema);

        Cinema actualCinema = cinemaService.createCinema("name",CinemaType.IMAX);

        verify(cinemaRepository, times(1)).save(expectedCinema);
        assertEquals(expectedCinema.getName(), actualCinema.getName());
        assertEquals(expectedCinema.getType(), actualCinema.getType());
    }

    @Test
    public void updateCinema_shouldUpdateAndSaveCinema() throws Exception{
        Long cinemaId = 1L;
        String name = "name";
        CinemaType type = CinemaType.IMAX;

        Cinema expectedCinema = new Cinema();
        expectedCinema.setName(name);
        expectedCinema.setType(type);

        when(cinemaRepository.findOne(cinemaId)).thenReturn(expectedCinema);
        when(cinemaRepository.save(expectedCinema)).thenReturn(expectedCinema);

        Cinema actualCinema = cinemaService.updateCinema(cinemaId, name, type);

        verify(cinemaRepository,times(1)).save(expectedCinema);
        assertEquals(expectedCinema.getName(), actualCinema.getName());
        assertEquals(expectedCinema.getType(), actualCinema.getType());
    }

    @Test
    public void deleteCinema_shouldDeleteCinema() throws Exception{
        Long cinemaId = 1L;
        String name = "name";
        CinemaType type = CinemaType.IMAX;

        Cinema expectedCinema = new Cinema();
        expectedCinema.setName(name);
        expectedCinema.setType(type);

        when(cinemaRepository.findOne(cinemaId)).thenReturn(expectedCinema);
        cinemaService.deleteCinema(cinemaId);

        verify(cinemaRepository,times(1)).delete(expectedCinema);
    }


}
