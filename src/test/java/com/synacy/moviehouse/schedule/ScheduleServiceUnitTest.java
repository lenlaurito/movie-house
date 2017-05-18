package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.exception.InvalidDataPassedException;
import com.synacy.moviehouse.exception.NoContentFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by steven on 5/16/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceUnitTest {

    private ScheduleService scheduleService;

    @Mock ScheduleRepository scheduleRepository;
    @Mock MovieService movieService;
    @Mock ScheduleUtils scheduleUtils;

    @Before
    public void setUp() throws Exception {
        scheduleService = new ScheduleService();
        scheduleService.setScheduleRepository(scheduleRepository);
        scheduleService.setMovieService(movieService);
        scheduleService.setScheduleUtils(scheduleUtils);
    }

//    @Test
//    public void fetchById_scheduleFound_shouldReturnSchedule() throws Exception {
//        long id = 1L;
//        Schedule schedule = new Schedule();
//        when(scheduleRepository.findOne(id)).thenReturn(schedule);
//
//        scheduleService.fetchById(id);
//
//        verify(scheduleRepository, times(1)).findOne(id);
//    }
//
//    @Test(expected = NoContentFoundException.class)
//    public void fetchById_scheduleNotFound_shouldThrowNoContentFoundException() throws Exception {
//        long id = 1L;
//        when(scheduleRepository.findOne(id)).thenReturn(null);
//
//        scheduleService.fetchById(id);
//    }
//
//    @Test
//    public void fetchAll_dateNotNullMovieNull_shouldReturnListOfAllScheduleByDate() throws Exception{
//        Date date = mock(Date.class);
//
//        List<Schedule> scheduleList = new ArrayList<>();
//        scheduleList.add(new Schedule());
//        scheduleList.add(new Schedule());
//
//        when(scheduleRepository.findAllByDate(date)).thenReturn(scheduleList);
//        when(scheduleUtils.dateStringParser("1995-2-2")).thenReturn(date);
//
//        scheduleService.fetchAll("1995-2-2",null);
//
//        verify(scheduleRepository, times(1)).findAllByDate(date);
//        assertEquals(scheduleList,scheduleRepository.findAllByDate(date));
//    }
//
//    @Test
//    public void fetchAll_dateNullMovieNotNull_shouldReturnListOfAllScheduleByMovie() throws Exception{
//        Movie movie = new Movie();
//        Long movieId = 1L;
//        List<Schedule> scheduleList = new ArrayList<>();
//        scheduleList.add(new Schedule());
//        scheduleList.add(new Schedule());
//
//        when(movieService.fetchById(movieId)).thenReturn(movie);
//        when(scheduleRepository.findAllByMovie(movie)).thenReturn(scheduleList);
//
//        scheduleService.fetchAll(null,movieId);
//
//        verify(scheduleRepository, times(1)).findAllByMovie(movie);
//        assertEquals(scheduleList,scheduleRepository.findAllByMovie(movie));
//    }
//
//    @Test
//    public void fetchAll_dateNotNullMovieNotNull_shouldReturnListOfAllScheduleByDateAndMovie() throws Exception{
//        Date date = mock(Date.class);
//        Movie movie = new Movie();
//        Long movieId = 1L;
//        List<Schedule> scheduleList = new ArrayList<>();
//        scheduleList.add(new Schedule());
//        scheduleList.add(new Schedule());
//
//        when(scheduleUtils.dateStringParser("1995-2-2")).thenReturn(date);
//        when(movieService.fetchById(movieId)).thenReturn(movie);
//        when(scheduleRepository.findAllByDateAndMovie(date,movie)).thenReturn(scheduleList);
//
//        scheduleService.fetchAll("1995-2-2",movieId);
//
//        verify(scheduleRepository, times(1)).findAllByDateAndMovie(date,movie);
//        assertEquals(scheduleList,scheduleRepository.findAllByDateAndMovie(date,movie));
//    }
//
//    @Test
//    public void fetchAll_dateNullMovieNull_shouldReturnListOfAllScheduleByDateAndMovie() throws Exception{
//        List<Schedule> scheduleList = new ArrayList<>();
//        scheduleList.add(new Schedule());
//        scheduleList.add(new Schedule());
//
//        when(scheduleRepository.findAll()).thenReturn(scheduleList);
//
//        scheduleService.fetchAll(null,null);
//
//        verify(scheduleRepository, times(1)).findAll();
//        assertEquals(scheduleList,scheduleRepository.findAll());
//    }
//
//    @Test
//    public void fetchAllPaginated_dateNotNullMovieNull_shouldReturnPageOfAllScheduleByDate() throws Exception{
//        Date date = mock(Date.class);
//        Page<Schedule> page = mock(Page.class);
//        PageRequest pageable = new PageRequest(0, 2);
//
//        when(scheduleUtils.dateStringParser("1995-2-2")).thenReturn(date);
//        when(scheduleRepository.findAllByDate(date, pageable)).thenReturn(page);
//
//        scheduleService.fetchAllPaginated("1995-2-2",null,0,2);
//
//        verify(scheduleRepository, times(1)).findAllByDate(date, pageable);
//        assertEquals(page,scheduleRepository.findAllByDate(date, pageable));
//    }
//
//    @Test
//    public void fetchAllPaginated_dateNullMovieNotNull_shouldReturnPageOfAllScheduleByMovie() throws Exception{
//        Movie movie = new Movie();
//        Long movieId = 1L;
//        Page<Schedule> page = mock(Page.class);
//        PageRequest pageRequest = new PageRequest(0, 2);
//
//        when(movieService.fetchById(movieId)).thenReturn(movie);
//        when(scheduleRepository.findAllByMovie(movie, pageRequest)).thenReturn(page);
//
//        scheduleService.fetchAllPaginated(null,movieId,0,2);
//
//        verify(scheduleRepository, times(1)).findAllByMovie(movie, pageRequest);
//        assertEquals(page,scheduleRepository.findAllByMovie(movie, pageRequest));
//    }
//
//    @Test
//    public void fetchAllPaginated_dateNotNullMovieNotNull_shouldReturnPageOfAllScheduleByDateAndMovie() throws Exception{
//        Date date = mock(Date.class);
//        Movie movie = new Movie();
//        Long movieId = 1L;
//        Page<Schedule> page = mock(Page.class);
//        PageRequest pageRequest = new PageRequest(0, 2);
//
//        when(scheduleUtils.dateStringParser("1995-2-2")).thenReturn(date);
//        when(movieService.fetchById(movieId)).thenReturn(movie);
//        when(scheduleRepository.findAllByDateAndMovie(date, movie, pageRequest)).thenReturn(page);
//
//        scheduleService.fetchAllPaginated("1995-2-2",movieId,0,2);
//
//        verify(scheduleRepository, times(1)).findAllByDateAndMovie(date, movie, pageRequest);
//        assertEquals(page,scheduleRepository.findAllByDateAndMovie(date, movie, pageRequest));
//    }
//
//    @Test
//    public void fetchAllPaginated_dateNullMovieNull_shouldReturnPageOfAllScheduleByDateAndMovie() throws Exception{
//        Page<Schedule> page = mock(Page.class);
//        PageRequest pageRequest = new PageRequest(0, 2);
//
//        when(scheduleRepository.findAll(pageRequest)).thenReturn(page);
//
//        scheduleService.fetchAllPaginated(null,null,0,2);
//
//        verify(scheduleRepository, times(1)).findAll(pageRequest);
//        assertEquals(page,scheduleRepository.findAll(pageRequest));
//    }
//
//    @Test
//    public void createSchedule_endDateTimeValidAndScheduleNotOverlapping_shouldCreateAndSaveSchedule() throws Exception{
//        Schedule schedule = new Schedule();
//        Movie movie = new Movie();
//        Cinema cinema = new Cinema();
//        Date startDateTime = mock(Date.class);
//        Date endDateTime = mock(Date.class);
//
//        movie.setDuration(20);
//        schedule.setMovie(movie);
//        schedule.setCinema(cinema);
//        schedule.setStartDateTime(startDateTime);
//        schedule.setEndDateTime(endDateTime);
//
//        when(scheduleUtils.isValidEndDateTime(schedule.getMovie().getDuration(),startDateTime,endDateTime)).thenReturn(true);
//        when(scheduleUtils.isDateOverlapping(schedule)).thenReturn(false);
//        when(scheduleRepository.save(schedule)).thenReturn(schedule);
//
//        scheduleService.createSchedule(movie,cinema,startDateTime,endDateTime);
//
//        verify(scheduleRepository, times(1)).save(schedule);
//        assertEquals(schedule,scheduleRepository.save(schedule));
//    }
//
//    @Test(expected = InvalidDataPassedException.class)
//    public void createSchedule_endDateTimeInvalidOrScheduleIsOverlapping_shouldThrowInvalidDataPassedException() throws Exception{
//        Schedule schedule = new Schedule();
//        Movie movie = new Movie();
//        Cinema cinema = new Cinema();
//        Date startDateTime = mock(Date.class);
//        Date endDateTime = mock(Date.class);
//
//        movie.setDuration(20);
//        schedule.setMovie(movie);
//        schedule.setCinema(cinema);
//        schedule.setStartDateTime(startDateTime);
//        schedule.setEndDateTime(endDateTime);
//
//        when(scheduleUtils.isValidEndDateTime(schedule.getMovie().getDuration(),startDateTime,endDateTime)).thenReturn(false);
//        when(scheduleUtils.isDateOverlapping(schedule)).thenReturn(true);
//
//        scheduleService.createSchedule(movie,cinema,startDateTime,endDateTime);
//    }
//
//    @Test
//    public void updateSchedule_endDateTimeValidAndScheduleNotOverlapping_shouldUpdateAndSaveSchedule() throws Exception{
//        Schedule schedule = new Schedule();
//        Movie movie = new Movie();
//        Cinema cinema = new Cinema();
//        Date startDateTime = mock(Date.class);
//        Date endDateTime = mock(Date.class);
//
//        movie.setDuration(20);
//        schedule.setMovie(movie);
//        schedule.setCinema(cinema);
//        schedule.setStartDateTime(startDateTime);
//        schedule.setEndDateTime(endDateTime);
//
//        when(scheduleUtils.isValidEndDateTime(schedule.getMovie().getDuration(),startDateTime,endDateTime)).thenReturn(true);
//        when(scheduleUtils.isDateOverlapping(schedule)).thenReturn(false);
//        when(scheduleRepository.save(schedule)).thenReturn(schedule);
//
//        scheduleService.updateSchedule(schedule,movie,cinema,startDateTime,endDateTime);
//
//        verify(scheduleRepository, times(1)).save(schedule);
//        assertEquals(schedule, scheduleRepository.save(schedule));
//    }
//
//    @Test(expected = InvalidDataPassedException.class)
//    public void updateSchedule_endDateTimeInvalidOrScheduleIsOverlapping_shouldThrowInvalidDataPassedException() throws Exception{
//        Schedule schedule = new Schedule();
//        Movie movie = new Movie();
//        Cinema cinema = new Cinema();
//        Date startDateTime = mock(Date.class);
//        Date endDateTime = mock(Date.class);
//
//        movie.setDuration(20);
//        schedule.setMovie(movie);
//        schedule.setCinema(cinema);
//        schedule.setStartDateTime(startDateTime);
//        schedule.setEndDateTime(endDateTime);
//
//        when(scheduleUtils.isValidEndDateTime(schedule.getMovie().getDuration(),startDateTime,endDateTime)).thenReturn(false);
//        when(scheduleUtils.isDateOverlapping(schedule)).thenReturn(true);
//
//        scheduleService.updateSchedule(schedule, movie,cinema,startDateTime,endDateTime);
//    }

}