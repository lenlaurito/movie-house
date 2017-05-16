package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exceptions.NoContentException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.synacy.moviehouse.cinema.CinemaType.IMAX;
import static com.synacy.moviehouse.cinema.CinemaType.STANDARD;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by kenichigouang on 5/15/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CinemaServiceTest {

    private CinemaService cinemaService;

    @Mock CinemaRepository cinemaRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cinemaService = new CinemaService();
        cinemaService.setCinemaRepository(cinemaRepository);
    }

    @Test
    public void fetchAll_mustReturnListOfObjects() throws Exception {
        List<Cinema> cinemas = mock(List.class);

        given(cinemaRepository.findAll()).willReturn(cinemas);

        assertEquals(cinemas, cinemaService.fetchAll());
    }

    @Test(expected = NoContentException.class)
    public void fetchAll_throwsNoContentExceptionOnEmptyList() throws Exception {
        List<Cinema> cinemas = new ArrayList<>();

        given(cinemaRepository.findAll()).willReturn(cinemas);

        assertEquals(cinemas, cinemaService.fetchAll());
    }

    @Test
    public void fetchAll_withExplicitTypeShouldReturnFilteredResult() throws Exception {
        List<Cinema> cinemas = new ArrayList<>();

        Cinema cinema1 = new Cinema();
        cinema1.setName("cinema1");
        cinema1.setType(STANDARD);
        Cinema cinema2 = new Cinema();
        cinema2.setName("cinema2");
        cinema2.setType(STANDARD);
        Cinema cinema3 = new Cinema();
        cinema3.setName("cinema3");
        cinema3.setType(IMAX);

        cinemas.add(cinema1);
        cinemas.add(cinema2);
        cinemas.add(cinema3);

        given(cinemaRepository.findAllByType(STANDARD)).willReturn(cinemas);

        assertEquals(cinemas, cinemaService.fetchAllByType(STANDARD));
    }

    @Test
    public void fetchById_mustReturnTheCorrectObject() throws Exception {
        Cinema cinema = new Cinema();

        cinema.setName("cinema");
        cinema.setType(STANDARD);

        given(cinemaRepository.findOne(cinema.getId())).willReturn(cinema);

        assertEquals(cinema, cinemaService.fetchById(cinema.getId()));
    }

    @Test(expected = NoContentException.class)
    public void fetchById_shouldThrowExceptionWhenIdIsNotFound() throws Exception {
        Cinema cinema = mock(Cinema.class);

        given(cinemaRepository.findOne(Long.valueOf(1))).willReturn(cinema);

        assertEquals(cinema, cinemaService.fetchById(Long.valueOf(2)));
    }

    @Test
    public void createCinema_shouldCreateCinemaWithCorrectFields() throws Exception {
        
    }

    @Test
    public void updateCinema() throws Exception {
    }

    @Test
    public void deleteCinema() throws Exception {
    }

    @Test
    public void fetchAllByType() throws Exception {
    }

}