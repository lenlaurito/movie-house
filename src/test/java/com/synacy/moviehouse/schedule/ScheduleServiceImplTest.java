package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.exception.ResourceNotFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import com.synacy.moviehouse.utilities.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private CinemaService cinemaService;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    public void fetchById_scheduleFound_returnSchedule() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDateTime = dateFormat.parse("2017-05-16 07:30:00");
        Date endDateTime = dateFormat.parse("2017-05-16 12:30:00");

        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);

        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);
        when(scheduleRepository.findOne(100L)).thenReturn(schedule);

        scheduleService.fetchScheduleById(100L);

        assertEquals(movie, schedule.getMovie());
        assertEquals(cinema, schedule.getCinema());
        assertEquals(startDateTime, schedule.getStartDateTime());
        assertEquals(endDateTime, schedule.getEndDateTime());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchById_scheduleNotFound_throwException() throws Exception {
        when(scheduleRepository.findOne(100L)).thenReturn(null);

        scheduleService.fetchScheduleById(100L);
        fail();
    }

    @Test
    public void fetchAllSchedules_withEmptySchedulesAndNullParameters() throws Exception {
        List<Schedule> schedules = new ArrayList<>();
        when(scheduleRepository.findAll()).thenReturn(schedules);

        List<Schedule> result = scheduleService.fetchAllSchedules(null, null);

        verify(scheduleRepository, times(1)).findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void fetchAllSchedules_withNullMovieId_returnCollectionOfSchedules() throws Exception {
        Date date = DateUtils.formatStringAsDate("2017-05-10 07:30:00");
        Date begDate = DateUtils.getBegTimeOfDate(date);
        Date endDate = DateUtils.getEndTimeOfDate(date);
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));
        when(scheduleRepository.findAllByStartDateTimeBetween(begDate, endDate)).thenReturn(schedules);

        List<Schedule> result = scheduleService.fetchAllSchedules(date, null);

        verify(scheduleRepository, times(1)).findAllByStartDateTimeBetween(begDate, endDate);
        assertEquals(3, result.size());
    }

    @Test
    public void fetchAllSchedules_withNullDate_returnCollectionOfSchedules() throws Exception {
        Long movieId = 100L;
        Movie movie = mock(Movie.class);
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));
        when(movieService.fetchMovieById(movieId)).thenReturn(movie);
        when(scheduleRepository.findAllByMovie(movie)).thenReturn(schedules);

        List<Schedule> result = scheduleService.fetchAllSchedules(null, movieId);

        verify(scheduleRepository, times(1)).findAllByMovie(movie);
        assertEquals(2, result.size());
    }

    @Test
    public void fetchAllSchedules_withSpecifiedParameters_returnCollectionOfSchedules() throws Exception {
        Long movieId = 100L;
        Date date = DateUtils.formatStringAsDate("2017-05-10 07:30:00");
        Date begDate = DateUtils.getBegTimeOfDate(date);
        Date endDate = DateUtils.getEndTimeOfDate(date);

        Movie movie = mock(Movie.class);
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));
        when(movieService.fetchMovieById(movieId)).thenReturn(movie);
        when(scheduleRepository.findAllByMovieAndStartDateTimeBetween(movie, begDate, endDate)).thenReturn(schedules);

        List<Schedule> result = scheduleService.fetchAllSchedules(date, movieId);

        verify(scheduleRepository, times(1)).findAllByMovieAndStartDateTimeBetween(movie, begDate, endDate);
        assertEquals(2, result.size());
    }

    @Test
    public void fetchAllSchedulesWithPagination_withEmptySchedulesAndNullParameters() throws Exception {
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "startDateTime");
        List<Schedule> schedules = new ArrayList<>();

        Page<Schedule> schedulePage = new PageImpl<>(schedules, pageable, schedules.size());
        when(scheduleRepository.findAll(pageable)).thenReturn(schedulePage);

        Page<Schedule> response = scheduleService.fetchAllSchedulesWithPagination(null, null, 0, 2);

        verify(scheduleRepository, times(1)).findAll(pageable);
        assertEquals(0, response.getTotalElements());
    }

    @Test
    public void fetchAllSchedulesWithPagination_withNullMovieId_returnCollectionOfSchedules() throws Exception {
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "startDateTime");
        Date date = DateUtils.formatStringAsDate("2017-05-10 07:30:00");
        Date begDate = DateUtils.getBegTimeOfDate(date);
        Date endDate = DateUtils.getEndTimeOfDate(date);
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));

        Page<Schedule> schedulePage = new PageImpl<>(schedules, pageable, schedules.size());
        when(scheduleRepository.findAllByStartDateTimeBetween(begDate, endDate, pageable)).thenReturn(schedulePage);

        Page<Schedule> response = scheduleService.fetchAllSchedulesWithPagination(date, null, 0, 2);

        verify(scheduleRepository, times(1)).findAllByStartDateTimeBetween(begDate, endDate, pageable);
        assertEquals(3, response.getTotalElements());
    }

    @Test
    public void fetchAllSchedulesWithPagination_withNullDate_returnCollectionOfSchedules() throws Exception {
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "startDateTime");
        Long movieId = 100L;
        Movie movie = mock(Movie.class);
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));

        Page<Schedule> schedulePage = new PageImpl<>(schedules, pageable, schedules.size());
        when(movieService.fetchMovieById(movieId)).thenReturn(movie);
        when(scheduleRepository.findAllByMovie(movie, pageable)).thenReturn(schedulePage);

        Page<Schedule> response = scheduleService.fetchAllSchedulesWithPagination(null, movieId, 0, 2);

        verify(scheduleRepository, times(1)).findAllByMovie(movie, pageable);
        assertEquals(2, response.getTotalElements());
    }

    @Test
    public void fetchAllSchedulesWithPagination_withSpecifiedParameters_returnCollectionOfSchedules() throws Exception {
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "startDateTime");
        Long movieId = 100L;
        Date date = DateUtils.formatStringAsDate("2017-05-10 07:30:00");
        Date begDate = DateUtils.getBegTimeOfDate(date);
        Date endDate = DateUtils.getEndTimeOfDate(date);

        Movie movie = mock(Movie.class);
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));

        Page<Schedule> schedulePage = new PageImpl<>(schedules, pageable, schedules.size());
        when(movieService.fetchMovieById(movieId)).thenReturn(movie);
        when(scheduleRepository.findAllByMovieAndStartDateTimeBetween(movie, begDate, endDate, pageable)).thenReturn(schedulePage);

        Page<Schedule> response = scheduleService.fetchAllSchedulesWithPagination(date, movieId, 0, 2);

        verify(scheduleRepository, times(1)).findAllByMovieAndStartDateTimeBetween(movie, begDate, endDate, pageable);
        assertEquals(2, response.getTotalElements());
    }

    @Test
    public void createSchedule_shouldAssertWithNewlyCreatedSchedule_withSpecifiedDetails() throws Exception {
        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);
        Date startDateTime = DateUtils.formatStringAsDate("2017-05-10 07:30:00");
        Date endDateTime = DateUtils.formatStringAsDate("2017-05-10 08:30:00");

        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        scheduleService.createSchedule(movie, cinema, startDateTime, endDateTime);

        verify(scheduleRepository, times(1)).save(schedule);
    }

    @Test
    public void updateSchedule_shouldUpdateAnExistingSchedule() throws Exception {
        Schedule schedule = mock(Schedule.class);
        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);
        Date mockDate = mock(Date.class);

        when(movieService.fetchMovieById(movie.getId())).thenReturn(movie);
        when(cinemaService.fetchCinemaById(cinema.getId())).thenReturn(cinema);

        scheduleService.updateSchedule(schedule, movie, cinema, mockDate, mockDate);

        verify(scheduleRepository, times(1)).save(schedule);
    }

    @Test
    public void deleteSchedule_shouldDeleteAnExistingSchedule() throws Exception {
        Schedule schedule = new Schedule();

        scheduleService.deleteSchedule(schedule);

        verify(scheduleRepository, times(1)).delete(schedule);
    }

}
