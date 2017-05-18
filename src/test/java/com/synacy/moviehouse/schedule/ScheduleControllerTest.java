package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private MovieService movieService;

    @Mock
    private CinemaService cinemaService;

    @InjectMocks
    ScheduleController scheduleController;

    @Test
    public void fetchSchedule_shouldGetACorrectSchedule() throws Exception {
        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);

        Long movieId = movie.getId();
        Long cinemaId = cinema.getId();

        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);
        when(movieService.fetchMovieById(movieId)).thenReturn(movie);
        when(cinemaService.fetchCinemaById(cinemaId)).thenReturn(cinema);

        Long scheduleId = 2L;
        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        when(scheduleService.fetchScheduleById(scheduleId)).thenReturn(schedule);

        ResponseEntity<Schedule> response = scheduleController.fetchSchedule(scheduleId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(movie, schedule.getMovie());
        assertEquals(cinema, schedule.getCinema());
        assertEquals(startDateTime, schedule.getStartDateTime());
        assertEquals(endDateTime, schedule.getEndDateTime());
    }

    @Test
    public void fetchAllSchedules_shouldRetrieveAllSchedules_withNullParameters() throws Exception {
        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        Schedule schedule1 = new Schedule();
        schedule1.setMovie(movie);
        schedule1.setCinema(cinema);
        schedule1.setStartDateTime(startDateTime);
        schedule1.setEndDateTime(endDateTime);

        Schedule schedule2 = new Schedule();
        schedule2.setMovie(movie);
        schedule2.setCinema(cinema);
        schedule2.setStartDateTime(startDateTime);
        schedule2.setEndDateTime(endDateTime);

        List<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule1);
        schedules.add(schedule2);

        when(scheduleService.fetchAllSchedules(null, null)).thenReturn(schedules);

        ResponseEntity<List<Schedule>> response = scheduleController.fetchAllSchedules(null, null, null,null);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(movie, response.getBody().get(0).getMovie());
        assertEquals(cinema, response.getBody().get(0).getCinema());
        assertEquals(startDateTime, response.getBody().get(0).getStartDateTime());
        assertEquals(endDateTime, response.getBody().get(0).getEndDateTime());

        assertEquals(movie, response.getBody().get(1).getMovie());
        assertEquals(cinema, response.getBody().get(1).getCinema());
        assertEquals(startDateTime, response.getBody().get(1).getStartDateTime());
        assertEquals(endDateTime, response.getBody().get(1).getEndDateTime());
    }

    @Test
    public void fetchAllSchedules_shouldRetrieveAllSchedules_withValidParametersAndPagination() throws Exception {
        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        Schedule schedule1 = new Schedule();
        schedule1.setMovie(movie);
        schedule1.setCinema(cinema);
        schedule1.setStartDateTime(startDateTime);
        schedule1.setEndDateTime(endDateTime);

        Schedule schedule2 = new Schedule();
        schedule2.setMovie(movie);
        schedule2.setCinema(cinema);
        schedule2.setStartDateTime(startDateTime);
        schedule2.setEndDateTime(endDateTime);

        List<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule1);
        schedules.add(schedule2);

        Page<Schedule> paginatedSchedules = mock(Page.class);
        when(paginatedSchedules.getContent()).thenReturn(schedules);

        when(scheduleService.fetchAllSchedulesWithPagination(startDateTime, 1L, 0, 2)).thenReturn(paginatedSchedules);

        ResponseEntity<Page<Schedule>> response = scheduleController.fetchAllSchedules(startDateTime, 1L, 0,2);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(movie, response.getBody().getContent().get(0).getMovie());
        assertEquals(cinema, response.getBody().getContent().get(0).getCinema());
        assertEquals(startDateTime, response.getBody().getContent().get(0).getStartDateTime());
        assertEquals(endDateTime, response.getBody().getContent().get(0).getEndDateTime());
        assertEquals(movie, response.getBody().getContent().get(1).getMovie());
        assertEquals(cinema, response.getBody().getContent().get(1).getCinema());
        assertEquals(startDateTime, response.getBody().getContent().get(1).getStartDateTime());
        assertEquals(endDateTime, response.getBody().getContent().get(1).getEndDateTime());
    }

    @Test
    public void createNewSchedule_shouldAssertWithNewlyCreatedScheduleWithSpecifiedDetails() throws Exception {
        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        Schedule newSchedule = new Schedule();
        newSchedule.setMovie(movie);
        newSchedule.setCinema(cinema);
        newSchedule.setStartDateTime(startDateTime);
        newSchedule.setEndDateTime(endDateTime);

        when(scheduleService.createSchedule(movie, cinema, startDateTime, endDateTime)).thenReturn(newSchedule);

        ResponseEntity<Schedule> response = scheduleController.createNewSchedule(newSchedule);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(movie, response.getBody().getMovie());
        assertEquals(cinema, response.getBody().getCinema());
        assertEquals(startDateTime, response.getBody().getStartDateTime());
        assertEquals(endDateTime, response.getBody().getEndDateTime());
    }

    @Test
    public void updateSchedule_shouldUpdateAnExistingSchedule() throws Exception {
        Long cinemaId = 2L;
        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        Schedule scheduleToUpdate = new Schedule();
        scheduleToUpdate.setMovie(movie);
        scheduleToUpdate.setCinema(cinema);
        scheduleToUpdate.setStartDateTime(startDateTime);
        scheduleToUpdate.setEndDateTime(endDateTime);

        when(scheduleService.fetchScheduleById(cinemaId)).thenReturn(scheduleToUpdate);
        when(scheduleService.updateSchedule(scheduleToUpdate, movie, cinema, startDateTime, endDateTime)).thenReturn(scheduleToUpdate);

        ResponseEntity<Schedule> response = scheduleController.updateSchedule(cinemaId, scheduleToUpdate);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(movie, response.getBody().getMovie());
        assertEquals(cinema, response.getBody().getCinema());
        assertEquals(startDateTime, response.getBody().getStartDateTime());
        assertEquals(endDateTime, response.getBody().getEndDateTime());
    }

    @Test
    public void deleteSchedule_shouldDeleteAnExistingScheduleGivenThatScheduleIdExist() throws Exception {
        Long scheduleId = 100L;

        Schedule schedule = new Schedule();

        when(scheduleService.fetchScheduleById(100L)).thenReturn(schedule);

        ResponseEntity<Cinema> response = scheduleController.deleteSchedule(scheduleId);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
    }

}