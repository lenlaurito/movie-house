package com.synacy.moviehouse.cinema;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by michael on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CinemaServiceTest {

    @Autowired
    CinemaService cinemaService;

    @Mock CinemaRepository cinemaRepository;

    @Before
    public void setUp() {
        cinemaService = new CinemaService();

        cinemaService.setCinemaRepository(cinemaRepository);
    }

    @Test
    public void fetchAllCinema_withNoFilter_shouldFindAll() throws Exception {
        String type = "";

        Page<Cinema> page = mock(Page.class);
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        PageRequest pageRequest = new PageRequest(0, 10, sort);

        List<Cinema> cinemas = new ArrayList<>();

        Cinema cinema1 = new Cinema();
        cinema1.setName("sample1");
        cinema1.setType("sample");

        Cinema cinema2 = new Cinema();
        cinema2.setName("sample2");
        cinema2.setType("sample");

        cinemas.add(cinema1);
        cinemas.add(cinema2);

        when(cinemaRepository.findAllCinemaByTypeContaining(type, pageRequest)).thenReturn(page);
        when(page.getContent()).thenReturn(cinemas);

        List<Cinema> fetchedCinema = cinemaService.fetchAllCinema(pageRequest, type);

        int expectedInvoccations = 1;

        verify(cinemaRepository, times(expectedInvoccations)).findAllCinemaByTypeContaining(
                ArgumentMatchers.eq(type),
                ArgumentMatchers.eq(pageRequest));

        assert fetchedCinema.get(0).equals(cinema1);
        assert fetchedCinema.get(1).equals(cinema2);
    }

    @Test
    public void fetchAllCinema_withFilter_shouldFindAllCinemaByTypeContaining() throws Exception {
        String type = "3d";

        Page<Cinema> page = mock(Page.class);
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        PageRequest pageRequest = new PageRequest(0, 10, sort);

        List<Cinema> cinemas = new ArrayList<>();

        Cinema cinema1 = new Cinema();
        cinema1.setName("sample1");
        cinema1.setType("sample");

        Cinema cinema2 = new Cinema();
        cinema2.setName("sample2");
        cinema2.setType("sample");

        cinemas.add(cinema1);
        cinemas.add(cinema2);

        when(cinemaRepository.findAllCinemaByTypeContaining(type, pageRequest)).thenReturn(page);
        when(page.getContent()).thenReturn(cinemas);

        List<Cinema> fetchedCinemas = cinemaService.fetchAllCinema(pageRequest, type);

        int expectedInvoccations = 1;

        verify(cinemaRepository, times(expectedInvoccations)).findAllCinemaByTypeContaining(
                ArgumentMatchers.eq(type),
                ArgumentMatchers.eq(pageRequest));

        assert  fetchedCinemas.size() == 2;
        assert fetchedCinemas.get(0).equals(cinema1);
        assert fetchedCinemas.get(1).equals(cinema2);
    }

    @Test
    public void createCinema_shouldSaveMovie() throws Exception {
        String name = "sample";
        String type = "sample type";

        Cinema cinema = new Cinema();

        cinema.setName(name);
        cinema.setType(type);

        when(cinemaRepository.save(Mockito.any(Cinema.class))).thenReturn(cinema);

        Cinema cinemaCreated = cinemaService.createCinema("sample", "sample");

        int expectedInvoccations = 1;

        verify(cinemaRepository, times(expectedInvoccations)).save(Mockito.any(Cinema.class));

        assert cinemaCreated.getName() == name;
        assert cinemaCreated.getType() == type;
    }

    @Test
    public void updateCinema_shouldSaveMovie() throws Exception {
        Long idToFind = new Long(1);

        String nameToUpdate = "any";
        String typeToUpdate = "test";

        Cinema cinema = new Cinema();
        cinema.setId(idToFind);

        when(cinemaRepository.findOne(idToFind)).thenReturn(cinema);
        when(cinemaRepository.save(cinema)).thenReturn(cinema);

        Cinema updatedCinema = cinemaService.updateCinema(idToFind, nameToUpdate, typeToUpdate);

        int expectedInvoccations = 1;

        verify(cinemaRepository, times(expectedInvoccations)).save(Mockito.any(Cinema.class));

        assert updatedCinema.getId() == idToFind;
        assert updatedCinema.getName() == nameToUpdate;
        assert updatedCinema.getType() == typeToUpdate;
    }

    @Test
    public void fetchCinemaById_exist_shouldReturnCinema() throws Exception {
        Long idToFind = new Long(1);

        Cinema cinema = new Cinema();
        cinema.setId(idToFind);

        when(cinemaRepository.findOne(idToFind)).thenReturn(cinema);

        Cinema found = cinemaService.fetchCinemaById(idToFind);

        int expectedInvocations = 1;

        verify(cinemaRepository, times(expectedInvocations)).findOne(idToFind);

        assert found.getId() == idToFind;
    }

    @Test(expected = NoResultException.class)
    public void fetchCinemaById_notExist_shouldThrowException() throws Exception {
        Long idToFind = new Long(1);

        cinemaService.fetchCinemaById(idToFind);
    }

    @Test
    public void deleteCinemaById_exist_shouldDeleteCinema() throws Exception {
        Long idToFind = new Long(1);

        Cinema cinema = mock(Cinema.class);

        when(cinemaRepository.findOne(idToFind)).thenReturn(cinema);

        cinemaService.deleteCinemaById(idToFind);

        int expectedInvocations = 1;

        verify(cinemaRepository, times(expectedInvocations)).findOne(ArgumentMatchers.eq(idToFind));
        verify(cinemaRepository, times(expectedInvocations)).delete(ArgumentMatchers.eq(cinema));
    }

    @Test(expected = NoResultException.class)
    public void deleteCinemaById_notExist_shouldThrowException() throws Exception {
        Long idToFind = new Long(1);

        cinemaService.fetchCinemaById(idToFind);
    }
}