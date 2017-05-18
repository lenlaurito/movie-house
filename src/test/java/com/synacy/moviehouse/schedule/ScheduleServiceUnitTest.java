package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.cinema.CinemaService;
import com.synacy.moviehouse.exception.InvalidDataPassedException;
import com.synacy.moviehouse.exception.NoContentFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import javafx.scene.input.DataFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;

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
    @Mock CinemaService cinemaService;

    @Before
    public void setUp() throws Exception {
        scheduleService = new ScheduleService();
        scheduleService.setScheduleRepository(scheduleRepository);
        scheduleService.setMovieService(movieService);
        scheduleService.setScheduleUtils(scheduleUtils);
        scheduleService.setCinemaService(cinemaService);
    }

    @Test
    public void fetchById_scheduleFound_shouldReturnSchedule() throws Exception {
        long id = 1L;
        Schedule expectedSchedule = new Schedule();

        when(scheduleRepository.findOne(id)).thenReturn(expectedSchedule);

        Schedule actualSchedule = scheduleService.fetchById(id);

        verify(scheduleRepository, times(1)).findOne(id);
        assertEquals(expectedSchedule, actualSchedule);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchById_scheduleNotFound_shouldThrowNoContentFoundException() throws Exception {
        when(scheduleRepository.findOne(1L)).thenReturn(null);
        scheduleService.fetchById(1L);
    }

    @Test
    public void fetchAll_dateNotNullMovieNull_shouldReturnListOfAllScheduleByDate() throws Exception{
        Date date = mock(Date.class);

        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule());
        expectedScheduleList.add(new Schedule());

        when(scheduleUtils.dateStringParser("1995-2-2")).thenReturn(date);
        when(scheduleRepository.findAllByDate(date)).thenReturn(expectedScheduleList);

        List<Schedule> actualScheduleList = scheduleService.fetchAll("1995-2-2",null);

        verify(scheduleRepository, times(1)).findAllByDate(date);
        assertEquals(expectedScheduleList,actualScheduleList);
        assertEquals(expectedScheduleList.size(),actualScheduleList.size());
    }

    @Test
    public void fetchAll_dateNullMovieNotNull_shouldReturnListOfAllScheduleByMovie() throws Exception{
        Long movieId = 1L;
        Movie movie = new Movie();

        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule());
        expectedScheduleList.add(new Schedule());

        when(movieService.fetchById(movieId)).thenReturn(movie);
        when(scheduleRepository.findAllByMovie(movie)).thenReturn(expectedScheduleList);

        List<Schedule> actualScheduleList = scheduleService.fetchAll(null,movieId);

        verify(scheduleRepository, times(1)).findAllByMovie(movie);
        assertEquals(expectedScheduleList,actualScheduleList);
        assertEquals(expectedScheduleList.size(),actualScheduleList.size());
    }

    @Test
    public void fetchAll_dateNotNullMovieNotNull_shouldReturnListOfAllScheduleByDateAndMovie() throws Exception{
        Long movieId = 1L;
        String dateInString = "1995-2-2";
        Date date = mock(Date.class);
        Movie movie = new Movie();

        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule());
        expectedScheduleList.add(new Schedule());

        when(scheduleUtils.dateStringParser(dateInString)).thenReturn(date);
        when(movieService.fetchById(movieId)).thenReturn(movie);
        when(scheduleRepository.findAllByDateAndMovie(date,movie)).thenReturn(expectedScheduleList);

        List<Schedule> actualScheduleList = scheduleService.fetchAll(dateInString,movieId);

        verify(scheduleRepository, times(1)).findAllByDateAndMovie(date,movie);
        assertEquals(expectedScheduleList,actualScheduleList);
        assertEquals(expectedScheduleList.size(),actualScheduleList.size());
    }

    @Test
    public void fetchAll_dateNullMovieNull_shouldReturnListOfAllScheduleByDateAndMovie() throws Exception{
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule());
        expectedScheduleList.add(new Schedule());

        when(scheduleRepository.findAll()).thenReturn(expectedScheduleList);

        List<Schedule> actualScheduleList = scheduleService.fetchAll(null,null);

        verify(scheduleRepository, times(1)).findAll();
        assertEquals(expectedScheduleList,actualScheduleList);
        assertEquals(expectedScheduleList.size(),actualScheduleList.size());
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchAll_schedulesNotFound_shouldThrowNoContentFoundException() throws Exception {
        List<Schedule> expectedScheduleList = new ArrayList<>();

        when(scheduleRepository.findAll()).thenReturn(expectedScheduleList);

        scheduleService.fetchAll(null,null);
    }

    @Test
    public void fetchAllPaginated_dateNotNullMovieNull_shouldReturnPageOfAllScheduleByDate() throws Exception{
        Date date = mock(Date.class);

        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "id");
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule());
        scheduleList.add(new Schedule());
        Page<Schedule> expectedSchedulePage = new PageImpl<>(scheduleList, pageable, scheduleList.size());

        when(scheduleUtils.dateStringParser("1995-2-2")).thenReturn(date);
        when(scheduleRepository.findAllByDate(date, new PageRequest(0,2))).thenReturn(expectedSchedulePage);

        Page<Schedule> actualSchedulePage = scheduleService.fetchAllPaginated("1995-2-2",null,0,2);

        verify(scheduleRepository, times(1)).findAllByDate(date, new PageRequest(0,2));
        assertEquals(expectedSchedulePage,actualSchedulePage);
        assertEquals(expectedSchedulePage.getTotalPages(),actualSchedulePage.getTotalPages());
        assertEquals(expectedSchedulePage.getTotalElements(),actualSchedulePage.getTotalElements());
    }

    @Test
    public void fetchAllPaginated_dateNullMovieNotNull_shouldReturnPageOfAllScheduleByMovie() throws Exception{
        Long movieId = 1L;
        Movie movie = new Movie();

        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "id");
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule());
        scheduleList.add(new Schedule());
        Page<Schedule> expectedSchedulePage = new PageImpl<>(scheduleList, pageable, scheduleList.size());

        when(movieService.fetchById(movieId)).thenReturn(movie);
        when(scheduleRepository.findAllByMovie(movie, new PageRequest(0, 2))).thenReturn(expectedSchedulePage);

        Page<Schedule> actualSchedulePage = scheduleService.fetchAllPaginated(null,movieId,0,2);

        verify(scheduleRepository, times(1)).findAllByMovie(movie, new PageRequest(0, 2));
        assertEquals(expectedSchedulePage,actualSchedulePage);
        assertEquals(expectedSchedulePage.getTotalPages(),actualSchedulePage.getTotalPages());
        assertEquals(expectedSchedulePage.getTotalElements(),actualSchedulePage.getTotalElements());
    }

    @Test
    public void fetchAllPaginated_dateNotNullMovieNotNull_shouldReturnPageOfAllScheduleByDateAndMovie() throws Exception{
        Long movieId = 1L;
        String dateInString = "1995-2-2";
        Date date = mock(Date.class);
        Movie movie = new Movie();

        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "id");
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule());
        scheduleList.add(new Schedule());
        Page<Schedule> expectedSchedulePage = new PageImpl<>(scheduleList, pageable, scheduleList.size());

        when(scheduleUtils.dateStringParser(dateInString)).thenReturn(date);
        when(movieService.fetchById(movieId)).thenReturn(movie);
        when(scheduleRepository.findAllByDateAndMovie(date, movie, new PageRequest(0, 2))).thenReturn(expectedSchedulePage);

        Page<Schedule> actualSchedulePage = scheduleService.fetchAllPaginated(dateInString,movieId,0,2);

        verify(scheduleRepository, times(1)).findAllByDateAndMovie(date, movie, new PageRequest(0, 2));
        assertEquals(expectedSchedulePage,actualSchedulePage);
        assertEquals(expectedSchedulePage.getTotalPages(),actualSchedulePage.getTotalPages());
        assertEquals(expectedSchedulePage.getTotalElements(),actualSchedulePage.getTotalElements());
    }

    @Test
    public void fetchAllPaginated_dateNullMovieNull_shouldReturnPageOfAllScheduleByDateAndMovie() throws Exception{
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "id");
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule());
        scheduleList.add(new Schedule());
        Page<Schedule> expectedSchedulePage = new PageImpl<>(scheduleList, pageable, scheduleList.size());

        when(scheduleRepository.findAll(new PageRequest(0, 2))).thenReturn(expectedSchedulePage);

        Page<Schedule> actualSchedulePage = scheduleService.fetchAllPaginated(null,null,0,2);

        verify(scheduleRepository, times(1)).findAll(new PageRequest(0, 2));
        assertEquals(expectedSchedulePage,actualSchedulePage);
        assertEquals(expectedSchedulePage.getTotalPages(),actualSchedulePage.getTotalPages());
        assertEquals(expectedSchedulePage.getTotalElements(),actualSchedulePage.getTotalElements());
    }

    @Test
    public void createSchedule_endDateTimeValidAndScheduleNotOverlapping_shouldCreateAndSaveSchedule() throws Exception{
        Long movieId = 1L;
        Long cinemaId = 2L;
        Schedule expectedSchedule = new Schedule();
        Movie movie = new Movie();
        Cinema cinema = new Cinema();
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        movie.setDuration(20);
        expectedSchedule.setMovie(movie);
        expectedSchedule.setCinema(cinema);
        expectedSchedule.setStartDateTime(startDateTime);
        expectedSchedule.setEndDateTime(endDateTime);

        when(movieService.fetchById(movieId)).thenReturn(movie);
        when(cinemaService.fetchById(cinemaId)).thenReturn(cinema);
        when(scheduleUtils.isValidEndDateTime(expectedSchedule.getMovie().getDuration(),startDateTime,endDateTime)).thenReturn(true);
        when(scheduleUtils.isDateOverlapping(expectedSchedule)).thenReturn(false);
        when(scheduleRepository.save(expectedSchedule)).thenReturn(expectedSchedule);

        Schedule actualSchedule = scheduleService.createSchedule(movieId,cinemaId,startDateTime,endDateTime);

        verify(scheduleRepository, times(1)).save(expectedSchedule);
        assertEquals(expectedSchedule,actualSchedule);
        assertEquals(expectedSchedule.getMovie().getId(), actualSchedule.getMovie().getId());
        assertEquals(expectedSchedule.getCinema().getId(), actualSchedule.getCinema().getId());
        assertEquals(expectedSchedule.getStartDateTime(), actualSchedule.getStartDateTime());
        assertEquals(expectedSchedule.getEndDateTime(), actualSchedule.getEndDateTime());
    }

    @Test(expected = InvalidDataPassedException.class)
    public void createSchedule_endDateTimeInvalidOrScheduleIsOverlapping_shouldThrowInvalidDataPassedException() throws Exception{
        Long movieId = 1L;
        Long cinemaId = 2L;
        Schedule expectedSchedule = new Schedule();
        Movie movie = new Movie();
        Cinema cinema = new Cinema();
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        movie.setDuration(20);
        expectedSchedule.setMovie(movie);

        when(movieService.fetchById(movieId)).thenReturn(movie);
        when(cinemaService.fetchById(cinemaId)).thenReturn(cinema);
        when(scheduleUtils.isValidEndDateTime(expectedSchedule.getMovie().getDuration(),startDateTime,endDateTime)).thenReturn(false);
        when(scheduleUtils.isDateOverlapping(expectedSchedule)).thenReturn(true);

        scheduleService.createSchedule(movieId,cinemaId,startDateTime,endDateTime);
    }

    @Test
    public void updateSchedule_endDateTimeValidAndScheduleNotOverlapping_shouldUpdateAndSaveSchedule() throws Exception{
        Long movieId = 1L;
        Long cinemaId = 2L;
        Long scheduleId = 3L;
        Schedule expectedSchedule = new Schedule();
        Movie movie = new Movie();
        Cinema cinema = new Cinema();
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        movie.setDuration(20);
        expectedSchedule.setMovie(movie);
        expectedSchedule.setCinema(cinema);
        expectedSchedule.setStartDateTime(startDateTime);
        expectedSchedule.setEndDateTime(endDateTime);

        when(scheduleRepository.findOne(scheduleId)).thenReturn(expectedSchedule);
        when(movieService.fetchById(movieId)).thenReturn(movie);
        when(cinemaService.fetchById(cinemaId)).thenReturn(cinema);
        when(scheduleUtils.isValidEndDateTime(expectedSchedule.getMovie().getDuration(),startDateTime,endDateTime)).thenReturn(true);
        when(scheduleUtils.isDateOverlapping(expectedSchedule)).thenReturn(false);
        when(scheduleRepository.save(expectedSchedule)).thenReturn(expectedSchedule);

        Schedule actualSchedule = scheduleService.updateSchedule(scheduleId,movieId,cinemaId,startDateTime,endDateTime);

        verify(scheduleRepository, times(1)).save(expectedSchedule);
        assertEquals(expectedSchedule,actualSchedule);
        assertEquals(expectedSchedule.getMovie().getId(), actualSchedule.getMovie().getId());
        assertEquals(expectedSchedule.getCinema().getId(), actualSchedule.getCinema().getId());
        assertEquals(expectedSchedule.getStartDateTime(), actualSchedule.getStartDateTime());
        assertEquals(expectedSchedule.getEndDateTime(), actualSchedule.getEndDateTime());
    }

    @Test(expected = InvalidDataPassedException.class)
    public void updateSchedule_endDateTimeInvalidOrScheduleIsOverlapping_shouldThrowInvalidDataPassedException() throws Exception{
        Long movieId = 1L;
        Long cinemaId = 2L;
        Long scheduleId = 3L;
        Schedule expectedSchedule = new Schedule();
        Movie movie = new Movie();
        Cinema cinema = new Cinema();
        Date startDateTime = mock(Date.class);
        Date endDateTime = mock(Date.class);

        movie.setDuration(20);
        expectedSchedule.setMovie(movie);

        when(scheduleRepository.findOne(scheduleId)).thenReturn(expectedSchedule);
        when(movieService.fetchById(movieId)).thenReturn(movie);
        when(cinemaService.fetchById(cinemaId)).thenReturn(cinema);
        when(scheduleUtils.isValidEndDateTime(expectedSchedule.getMovie().getDuration(),startDateTime,endDateTime)).thenReturn(false);
        when(scheduleUtils.isDateOverlapping(expectedSchedule)).thenReturn(true);

        Schedule actualSchedule = scheduleService.updateSchedule(scheduleId,movieId,cinemaId,startDateTime,endDateTime);
    }

    @Test
    public void deleteMovie_shouldDeleteMovie() throws Exception{
        Long scheduleId = 3L;
        Schedule schedule = new Schedule();

        when(scheduleRepository.findOne(scheduleId)).thenReturn(schedule);

        scheduleService.deleteSchedule(scheduleId);

        verify(scheduleRepository,times(1)).delete(schedule);
    }

}