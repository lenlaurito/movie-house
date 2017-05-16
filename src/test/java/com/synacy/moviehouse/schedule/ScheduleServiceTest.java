package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.ResourceAlreadyExistException;
import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by banjoe on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    private ScheduleService scheduleService;
    @Mock ScheduleRepo scheduleRepo;

    @Before
    public void setUp() throws Exception {
        scheduleService = new ScheduleServiceImpl();
        scheduleService.setScheduleRepo(scheduleRepo);
    }

    @Test
    public void saveNewSchedule_shouldSaveNewSchedule() throws Exception {
        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        scheduleService.saveNewSchedule(movie, cinema, new Date(), new Date());
        verify(scheduleRepo, times(1)).save(new Schedule());
    }

    @Test
    public void fetchAllSchedule_shouldReturnAllScheduleWithNoSpecificFilter() throws Exception {
        scheduleService.fetchAllSchedule(null, null);
        verify(scheduleRepo, times(1)).findAll();
    }

    @Test
    public void fetchAllSchedule_shouldReturnAllScheduleFilteredByDate() throws Exception {
        scheduleService.fetchAllSchedule("2017-01-26 09:00:00 +0800", null);
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 23:59:00");

        verify(scheduleRepo, times(1)).findAllByStartDateTimeBetween(startDate, endDate);
    }

    @Test
    public void fetchAllSchedule_shouldReturnAllScheduleFilteredByMovieId() throws Exception {
        scheduleService.fetchAllSchedule(null, 200L);
        verify(scheduleRepo, times(1)).findAllByMovieId(200L);
    }

    @Test
    public void fetchAllSchedule_shouldReturnAllScheduleFilteredByDateAndMovieId() throws Exception {
        scheduleService.fetchAllSchedule("2017-01-26 09:00:00 +0800", 200L);
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 23:59:00");

        verify(scheduleRepo, times(1)).findAllByStartDateTimeBetweenAndMovieId(startDate, endDate, 200L);
    }

    @Test
    public void fetchPaginatedSchedule_shouldReturnPaginatedListWithNoSpecificFilter() throws Exception {
        scheduleService.fetchPaginatedSchedule(null, null, 0, 1);
        verify(scheduleRepo, times(1)).findAll(new PageRequest(0, 1));
    }

    @Test
    public void fetchPaginatedSchedule_shouldReturnPaginatedListFilteredByDate() throws Exception {
        scheduleService.fetchPaginatedSchedule("2017-01-26 09:00:00 +0800", null, 0, 1);
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 23:59:00");

        verify(scheduleRepo, times(1)).findAllByStartDateTimeBetween(startDate, endDate, new PageRequest(0, 1));
    }

    @Test
    public void fetchPaginatedSchedule_shouldReturnPaginatedListFilteredByMovieId() throws Exception {
        scheduleService.fetchPaginatedSchedule(null, 200L, 0, 1);
        verify(scheduleRepo, times(1)).findAllByMovieId(200L, new PageRequest(0, 1));
    }

    @Test
    public void fetchPaginatedSchedule_shouldReturnPaginatedListFilteredByDateAndMovieId() throws Exception {
        scheduleService.fetchPaginatedSchedule("2017-01-26 09:00:00 +0800", 200L, 0, 1);
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 23:59:00");

        verify(scheduleRepo, times(1)).findAllByStartDateTimeBetweenAndMovieId(startDate, endDate, 200L, new PageRequest(0, 1));
    }


    @Test
    public void updateSchedule_shouldUpdateTheExistingSchedule() throws Exception {
        Schedule schedule = new Schedule();
        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        scheduleService.updateSchedule(schedule, movie, cinema, new Date(), new Date());
        verify(scheduleRepo, times(1)).save(eq(schedule));
    }

    @Test(expected = ResourceAlreadyExistException.class)
    public void saveNewSchedule_shouldThrowResourceAlreadyExistExceptionIfThereIsConflictWithTheSchedule() {
        Date startDateTime = new Date();
        Date endDateTime = new Date();

        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        List<Schedule> list = new ArrayList<>();
        list.add(mock(Schedule.class));
        when(scheduleRepo.findAllByStartDateTimeBetweenAndCinemaId(startDateTime, endDateTime, cinema.getId())).thenReturn(list);


        scheduleService.saveNewSchedule(movie, cinema, startDateTime, endDateTime);
        verify(scheduleRepo, times(1)).save(new Schedule());
    }

}
