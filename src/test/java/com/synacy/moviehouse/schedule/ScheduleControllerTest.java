package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.MissingParameterException;
import com.synacy.moviehouse.ResourceNotFoundException;
import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by banjoe on 5/15/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class ScheduleControllerTest {

    private ScheduleController scheduleController;
    @Mock
    ScheduleService scheduleService;
    @Mock
    MovieService movieService;
    @Mock
    CinemaService cinemaService;

    @Before
    public void setUp() throws Exception {
        scheduleController = new ScheduleController();
        scheduleController.setScheduleService(scheduleService);
        scheduleController.setMovieService(movieService);
        scheduleController.setCinemaService(cinemaService);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchScheduleById_shouldThrowResourceNotFoundExceptionWhenScheduleIsReturnNull() throws Exception {
        Long scheduleId = 200L;

        scheduleController.fetchScheduleById(scheduleId);
        verify(scheduleService, times(1)).fetchScheduleById(eq(scheduleId));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchAllSchedule_shouldThrowResourceNotFoundExceptionWhenListReturnsEmpty() throws Exception {
        String date = "2017-01-01 10:00:00 +0800";
        Long movieId = 100L;

        scheduleController.fetchAllSchedule(null, null, date, 100L);
        verify(scheduleService, times(1)).fetchAllSchedule(eq(date), eq(movieId));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchAllSchedule_shouldThrowResourceNotFoundExceptionWhenPageReturnsEmpty() throws Exception {
        String date = "2017-01-01 10:00:00 +0800";
        Long movieId = 100L;
        Integer offset = 0, max = 2;
        List<Schedule> list = new ArrayList<>();
        when(scheduleService.fetchPaginatedSchedule(date, movieId, offset, max)).thenReturn(new PageImpl<Schedule>(list, new PageRequest(offset, max), list.size()));

        scheduleController.fetchAllSchedule(offset, max, date, 100L);
        verify(scheduleService, times(1)).fetchPaginatedSchedule(eq(date), eq(movieId), eq(offset), eq(max));
    }

    @Test(expected = MissingParameterException.class)
    public void fetchAllSchedule_shouldThrowMissingParameterExceptionIfOffsetIsNullAndMaxIsNotNull() throws Exception {
        Long movieId = 200L;

        scheduleController.fetchAllSchedule(null, 1, null, null);
        verify(movieService, times(1)).fetchMovieById(eq(movieId));
    }

    @Test(expected = MissingParameterException.class)
    public void fetchAllSchedule_shouldThrowMissingParameterExceptionIfOffsetIsNotNullAndMaxIsNull() throws Exception {
        Long movieId = 200L;

        scheduleController.fetchAllSchedule(0, null, null, null);
        verify(movieService, times(1)).fetchMovieById(eq(movieId));
    }

    @Test
    public void createSchedule_shouldOnlyInvokeOnce() throws Exception {
        Schedule schedule = new Schedule();

        Movie movie = new Movie();
        movie.setName("Movie");
        movie.setDescription("Sample Description");
        movie.setDuration(2);
        movie.setGenre("Horror");
        movie.setId(200L);
        when(movieService.fetchMovieById(eq(movie.getId()))).thenReturn(movie);

        Cinema cinema = new Cinema();
        cinema.setId(200L);
        cinema.setType("3D");
        cinema.setName("Cinema 1");
        when(cinemaService.fetchCinemaById(eq(cinema.getId()))).thenReturn(cinema);

        Date startDate = new Date();
        Date endDate = new Date();

        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDate);
        schedule.setEndDateTime(endDate);

        scheduleController.createSchedule(schedule);

        BDDMockito.then(scheduleService).should(times(1)).saveNewSchedule(eq(movie), eq(cinema), eq(startDate), eq(endDate));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createSchedule_shouldThrowResourceNotFoundExceptionWhenMovieIsReturnNull() throws Exception {
        Schedule schedule = new Schedule();

        Movie movie = new Movie();
        movie.setName("Movie");
        movie.setDescription("Sample Description");
        movie.setDuration(2);
        movie.setGenre("Horror");
        movie.setId(200L);
        when(movieService.fetchMovieById(eq(movie.getId()))).thenReturn(movie);

        Cinema cinema = new Cinema();

        Date startDate = new Date();
        Date endDate = new Date();

        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDate);
        schedule.setEndDateTime(endDate);

        scheduleController.createSchedule(schedule);

        BDDMockito.then(scheduleService).should(times(1)).saveNewSchedule(eq(movie), eq(cinema), eq(startDate), eq(endDate));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createSchedule_shouldThrowResourceNotFoundExceptionWhenCinemaIsReturnNull() throws Exception {
        Schedule schedule = new Schedule();

        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        Date startDate = new Date();
        Date endDate = new Date();

        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDate);
        schedule.setEndDateTime(endDate);

        scheduleController.createSchedule(schedule);

        BDDMockito.then(scheduleService).should(times(1)).saveNewSchedule(eq(movie), eq(cinema), eq(startDate), eq(endDate));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateSchedule_shouldThrowResourceNotFoundExceptionWhenScheduleIsReturnNull() throws Exception {
        Long scheduleId = 200L;
        Schedule schedule = new Schedule();

        scheduleController.updateSchedule(scheduleId, schedule);
        verify(scheduleService, times(1)).fetchScheduleById(eq(scheduleId));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteSchedule_shouldThrowResourceNotFoundExceptionWhenScheduleIsReturnNull() throws Exception {
        Long scheduleId = 200L;

        scheduleController.deleteSchedule(scheduleId);
        verify(scheduleService, times(1)).fetchScheduleById(eq(scheduleId));
    }

}
