package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.exception.IncompleteInformationException;
import com.synacy.moviehouse.exception.InvalidParameterException;
import com.synacy.moviehouse.exception.NoContentFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by steven on 5/16/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduleControllerUnitTest {

    @Autowired
    ScheduleController scheduleController;

    @Mock ScheduleService scheduleService;
    @Mock MovieService movieService;
    @Mock CinemaService cinemaService;

    @Before
    public void setUp() throws Exception {
       scheduleController = new ScheduleController();

       scheduleController.setScheduleService(scheduleService);
       scheduleController.setMovieService(movieService);
       scheduleController.setCinemaService(cinemaService);
    }

    @Test
    public void fetchSchedule_shouldReturnSchedule() throws Exception {
        Long scheduleId = 1L;

        scheduleController.fetchSchedule(scheduleId);

        verify(scheduleService,times(1)).fetchById(scheduleId);
    }

    @Test
    public void fetchAllSchedule_offsetAndMaxAreNull_shouldReturnListOfAllSchedule() throws Exception{
        Schedule schedule = new Schedule();
        String dateInString = "2015-2-18";
        long movieId = 1L;

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule);

        when(scheduleService.fetchAll(dateInString, movieId)).thenReturn(scheduleList);

        scheduleController.fetchAllSchedule(null,null, dateInString, movieId);

        verify(scheduleService, times(1)).fetchAll(dateInString, movieId);
        assertEquals(scheduleList, scheduleService.fetchAll(dateInString, movieId));
    }

    @Test
    public void fetchAllSchedule_offsetAndMaxAreNotNullSchedulesFound_shouldReturnListOfAllPaginatedSchedule() throws Exception{

        String dateInString = "2015-2-18";
        long movieId = 1L;

        Page<Schedule> schedulePage = mock(Page.class);

        when(schedulePage.getTotalPages()).thenReturn(1);
        when(scheduleService.fetchAllPaginated(dateInString, movieId,0,2)).thenReturn(schedulePage);

        scheduleController.fetchAllSchedule(0,2, dateInString, movieId);

        verify(scheduleService, times(1)).fetchAllPaginated(dateInString, movieId,0,2);
        assertEquals(schedulePage, scheduleService.fetchAllPaginated(dateInString, movieId,0,2));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchAllSchedule_offsetAndMaxAreNotNullSchedulesNotFound_shouldThrowNoContentFoundException() throws Exception{
        String dateInString = "2015-2-18";
        long movieId = 1L;
        Page<Schedule> schedulePage = mock(Page.class);

        when(scheduleService.fetchAllPaginated(dateInString, movieId,0,2)).thenReturn(schedulePage);

        scheduleController.fetchAllSchedule(0,2, dateInString, movieId);
    }

    @Test(expected = InvalidParameterException.class)
    public void fetchAllSchedule_offsetNullAndMaxNotNullViceVersa_shouldThrowInvalidParameterException() throws Exception{
        String dateInString = "2015-2-18";
        long movieId = 1L;
        Page<Schedule> schedulePage = mock(Page.class);

        when(scheduleService.fetchAllPaginated(dateInString, movieId,null,2)).thenReturn(schedulePage);

        scheduleController.fetchAllSchedule(null,2, dateInString, movieId);
    }

    @Test
    public void createSchedule_scheduleHasAllValidInputs_shouldCreateAndReturnSchedule() throws Exception{
        Schedule schedule = mock(Schedule.class);

        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        when(schedule.getMovie()).thenReturn(movie);
        when(schedule.getCinema()).thenReturn(cinema);
        when(schedule.getStartDateTime()).thenReturn(startDateTime);
        when(schedule.getEndDateTime()).thenReturn(endDateTime);
        when(movieService.fetchById(schedule.getMovie().getId())).thenReturn(movie);
        when(cinemaService.fetchById(schedule.getCinema().getId())).thenReturn(cinema);

        scheduleController.createSchedule(schedule);

        verify(scheduleService, times(1)).createSchedule(movie,cinema,startDateTime,endDateTime);
    }

    @Test(expected = IncompleteInformationException.class)
    public void createSchedule_scheduleHasEitherAllInputsAreInvalid_shouldThrowIncompleteInformationException() throws Exception{
        Schedule schedule = mock(Schedule.class);
        scheduleController.createSchedule(schedule);
    }

    @Test
    public void updateSchedule_updateScheduleAllInputsValid_shouldUpdateAndReturnSchedule() throws  Exception{
        Schedule schedule = mock(Schedule.class);
        Schedule scheduleToBeUpdated = mock(Schedule.class);

        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        when(schedule.getMovie()).thenReturn(movie);
        when(schedule.getCinema()).thenReturn(cinema);
        when(schedule.getStartDateTime()).thenReturn(startDateTime);
        when(schedule.getEndDateTime()).thenReturn(endDateTime);
        when(movieService.fetchById(schedule.getMovie().getId())).thenReturn(movie);
        when(cinemaService.fetchById(schedule.getCinema().getId())).thenReturn(cinema);
        when(scheduleService.fetchById(1L)).thenReturn(scheduleToBeUpdated);

        scheduleController.updateSchedule(1L, schedule);
        verify(scheduleService, times(1)).updateSchedule(scheduleToBeUpdated, movie,cinema,startDateTime,endDateTime);
    }

    @Test(expected = IncompleteInformationException.class)
    public void updateSchedule_scheduleHasEitherAllInputsAreInvalid_shouldThrowIncompleteInformationException() throws Exception{
        Schedule schedule = mock(Schedule.class);
        scheduleController.updateSchedule(1L,schedule);
    }

    @Test
    public void deleteSchedule_shouldDeleteSchedule() throws Exception{
        Schedule schedule = new Schedule();
        long scheduleId = 1L;
        when(scheduleService.fetchById(scheduleId)).thenReturn(schedule);

        scheduleController.deleteSchedule(scheduleId);

        verify(scheduleService, times(1)).deleteSchedule(schedule);
    }

}