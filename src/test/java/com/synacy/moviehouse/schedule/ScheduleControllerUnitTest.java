package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.exception.IncompleteInformationException;
import com.synacy.moviehouse.exception.InvalidParameterException;
import com.synacy.moviehouse.movie.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by steven on 5/16/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduleControllerUnitTest {

    @Autowired
    ScheduleController scheduleController;

    @Mock ScheduleService scheduleService;

    @Before
    public void setUp() throws Exception {
       scheduleController = new ScheduleController();

       scheduleController.setScheduleService(scheduleService);
    }

    @Test
    public void fetchSchedule_shouldReturnSchedule() throws Exception {
        Long scheduleId = 1L;
        Schedule expectedSchedule = new Schedule();
        Movie movie = new Movie();
        Cinema cinema = new Cinema();
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        expectedSchedule.setMovie(movie);
        expectedSchedule.setCinema(cinema);
        expectedSchedule.setStartDateTime(startDateTime);
        expectedSchedule.setEndDateTime(endDateTime);

        when(scheduleService.fetchById(scheduleId)).thenReturn(expectedSchedule);

        Schedule actualSchedule = scheduleController.fetchSchedule(scheduleId);

        verify(scheduleService,times(1)).fetchById(scheduleId);
        assertEquals(expectedSchedule, actualSchedule);
        assertEquals(expectedSchedule.getMovie().getId(), actualSchedule.getMovie().getId());
        assertEquals(expectedSchedule.getCinema().getId(), actualSchedule.getCinema().getId());
        assertEquals(expectedSchedule.getStartDateTime(), actualSchedule.getStartDateTime());
        assertEquals(expectedSchedule.getEndDateTime(), actualSchedule.getEndDateTime());
    }

    @Test
    public void fetchAllSchedule_offsetAndMaxAreNull_shouldReturnListOfAllSchedule() throws Exception{
        long movieId = 1L;
        String dateInString = "2015-2-18";

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule());
        scheduleList.add(new Schedule());

        ResponseEntity expectedResponseEntity = ResponseEntity.ok().body(scheduleList);

        when(scheduleService.fetchAll(dateInString, movieId)).thenReturn(scheduleList);

        ResponseEntity actualResponseEntity = scheduleController.fetchAllSchedule(null,null, dateInString, movieId);

        verify(scheduleService, times(1)).fetchAll(dateInString, movieId);
        assertEquals(expectedResponseEntity, actualResponseEntity);
        assertEquals(expectedResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode());
    }

    @Test
    public void fetchAllSchedule_offsetAndMaxAreNotNullSchedulesFound_shouldReturnListOfAllPaginatedSchedule() throws Exception{
        long movieId = 1L;
        String dateInString = "2015-2-18";

        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "name");
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule());
        scheduleList.add(new Schedule());
        Page<Schedule> schedulePage = new PageImpl<>(scheduleList, pageable, scheduleList.size());

        ResponseEntity expectedResponseEntity = ResponseEntity.ok().body(schedulePage);

        when(scheduleService.fetchAllPaginated(dateInString, movieId,0,2)).thenReturn(schedulePage);

        ResponseEntity actualResponseEntity = scheduleController.fetchAllSchedule(0,2, dateInString, movieId);

        verify(scheduleService, times(1)).fetchAllPaginated(dateInString, movieId,0,2);
        assertEquals(expectedResponseEntity, actualResponseEntity);
        assertEquals(expectedResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode());
    }

    @Test(expected = InvalidParameterException.class)
    public void fetchAllSchedule_offsetNullAndMaxNotNullViceVersa_shouldThrowInvalidParameterException() throws Exception{
        scheduleController.fetchAllSchedule(null,2, "2015-2-18", 1L);
    }

    @Test
    public void createSchedule_scheduleHasAllValidInputs_shouldCreateAndReturnSchedule() throws Exception{
        Schedule expectedSchedule = new Schedule();
        Movie movie = new Movie();
        Cinema cinema = new Cinema();
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        expectedSchedule.setMovie(movie);
        expectedSchedule.setCinema(cinema);
        expectedSchedule.setStartDateTime(startDateTime);
        expectedSchedule.setEndDateTime(endDateTime);

        when(scheduleService.createSchedule(movie.getId(),cinema.getId(),startDateTime,endDateTime)).thenReturn(expectedSchedule);

        Schedule actualSchedule = scheduleController.createSchedule(expectedSchedule);

        verify(scheduleService, times(1)).createSchedule(movie.getId(),cinema.getId(),startDateTime,endDateTime);
        assertEquals(expectedSchedule,actualSchedule);
        assertEquals(expectedSchedule.getMovie().getId(), actualSchedule.getMovie().getId());
        assertEquals(expectedSchedule.getCinema().getId(), actualSchedule.getCinema().getId());
        assertEquals(expectedSchedule.getStartDateTime(), actualSchedule.getStartDateTime());
        assertEquals(expectedSchedule.getEndDateTime(), actualSchedule.getEndDateTime());
    }

    @Test(expected = IncompleteInformationException.class)
    public void createSchedule_scheduleHasEitherAllInputsAreInvalid_shouldThrowIncompleteInformationException() throws Exception{
        Schedule schedule = mock(Schedule.class);
        scheduleController.createSchedule(schedule);
    }

    @Test
    public void updateSchedule_updateScheduleAllInputsValid_shouldUpdateAndReturnSchedule() throws  Exception{
        Long scheduleId = 1L;
        Schedule expectedSchedule = new Schedule();
        Movie movie = new Movie();
        Cinema cinema = new Cinema();
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        expectedSchedule.setMovie(movie);
        expectedSchedule.setCinema(cinema);
        expectedSchedule.setStartDateTime(startDateTime);
        expectedSchedule.setEndDateTime(endDateTime);

        when(scheduleService.updateSchedule(scheduleId, movie.getId(), cinema.getId(), startDateTime, endDateTime)).thenReturn(expectedSchedule);

        Schedule actualSchedule = scheduleController.updateSchedule(scheduleId, expectedSchedule);

        verify(scheduleService, times(1)).updateSchedule(scheduleId, movie.getId(), cinema.getId(), startDateTime, endDateTime);
        assertEquals(expectedSchedule,actualSchedule);
        assertEquals(expectedSchedule.getMovie().getId(), actualSchedule.getMovie().getId());
        assertEquals(expectedSchedule.getCinema().getId(), actualSchedule.getCinema().getId());
        assertEquals(expectedSchedule.getStartDateTime(), actualSchedule.getStartDateTime());
        assertEquals(expectedSchedule.getEndDateTime(), actualSchedule.getEndDateTime());
    }

    @Test(expected = IncompleteInformationException.class)
    public void updateSchedule_scheduleHasEitherAllInputsAreInvalid_shouldThrowIncompleteInformationException() throws Exception{
        Schedule schedule = new Schedule();
        scheduleController.updateSchedule(1L,schedule);
    }

    @Test
    public void deleteSchedule_shouldDeleteSchedule() throws Exception{
        long scheduleId = 1L;

        scheduleController.deleteSchedule(scheduleId);

        verify(scheduleService, times(1)).deleteSchedule(scheduleId);
    }

}