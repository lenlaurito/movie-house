package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by michael on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)

public class ScheduleControllerTest {

    ScheduleController scheduleController;

    @Mock
    ScheduleService scheduleService;

    @Before
    public void setUp() throws Exception {
        scheduleController = new ScheduleController();

        scheduleController.scheduleService = scheduleService;
    }

    @Test
    public void fetchAllSchedules() throws Exception {
        Sort sort = new Sort(Sort.Direction.ASC, "startDateTime");
        Pageable pageable = new PageRequest(0, 1, sort);

        scheduleController.fetchAllSchedules(pageable, null, null);

        verify(scheduleService, times(1)).fetchAllSchedules(
                ArgumentMatchers.eq(pageable),
                ArgumentMatchers.eq(null),
                ArgumentMatchers.eq(null)
        );
    }

    @Test
    public void fetchAllSchedules_withFilterDate() throws Exception {
        String date = "2017-01-01";

        Sort sort = new Sort(Sort.Direction.ASC, "startDateTime");
        Pageable pageable = new PageRequest(0, 1, sort);

        scheduleController.fetchAllSchedules(pageable, date, "");

        verify(scheduleService, times(1)).fetchAllSchedules(ArgumentMatchers.eq(pageable),
                ArgumentMatchers.eq(date),
                ArgumentMatchers.eq(""));
    }

    @Test
    public void createSchedule() throws Exception {
        Schedule schedule = mock(Schedule.class);

        Date start = mock(Date.class);
        Date end   = mock(Date.class);

        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);

        when(schedule.getStartDateTime()).thenReturn(start);
        when(schedule.getEndDateTime()).thenReturn(end);
        when(schedule.getMovie()).thenReturn(movie);
        when(schedule.getCinema()).thenReturn(cinema);

        scheduleController.createSchedule(schedule);


        verify(scheduleService, times(1)).createSchedule(
                ArgumentMatchers.eq(start),
                ArgumentMatchers.eq(end),
                ArgumentMatchers.eq(movie),
                ArgumentMatchers.eq(cinema)
        );
    }

    @Test
    public void fetchScheduleById() throws Exception {
        Long idToFind = new Long(1);

        scheduleController.fetchScheduleById(idToFind);

        verify(scheduleService, times(1)).fetchScheduleById(
                ArgumentMatchers.eq(idToFind)
        );
    }

    @Test
    public void updateSchedule() throws Exception {
        Long idToFind = new Long(1);
        Schedule schedule = mock(Schedule.class);
        Schedule scheduleRequest = mock(Schedule.class);

        Date start = mock(Date.class);
        Date end   = mock(Date.class);

        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);

        when(scheduleRequest.getStartDateTime()).thenReturn(start);
        when(scheduleRequest.getEndDateTime()).thenReturn(end);
        when(scheduleRequest.getMovie()).thenReturn(movie);
        when(scheduleRequest.getCinema()).thenReturn(cinema);

        scheduleController.updateSchedule(idToFind, scheduleRequest);


        verify(scheduleService, times(1)).updateSchedule(
                ArgumentMatchers.eq(idToFind),
                ArgumentMatchers.eq(start),
                ArgumentMatchers.eq(end),
                ArgumentMatchers.eq(movie),
                ArgumentMatchers.eq(cinema)
        );
    }

    @Test
    public void deleteScheduleById() throws Exception {
        Long idToFind = new Long(1);

        scheduleController.deleteScheduleById(idToFind);

        verify(scheduleService, times(1)).deleteScheduleById(ArgumentMatchers.eq(idToFind));
    }

}