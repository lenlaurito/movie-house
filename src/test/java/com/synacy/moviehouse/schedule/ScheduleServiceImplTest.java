package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.exception.ResourceNotFoundException;
import com.synacy.moviehouse.movie.Movie;
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

        scheduleService.fetchById(100L);

        assertEquals(movie, schedule.getMovie());
        assertEquals(cinema, schedule.getCinema());
        assertEquals(startDateTime, schedule.getStartDateTime());
        assertEquals(endDateTime, schedule.getEndDateTime());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fetchById_scheduleNotFound_throwException() throws Exception {
        when(scheduleRepository.findOne(100L)).thenReturn(null);
        scheduleService.fetchById(100L);
        fail();
    }

    @Test
    public void fetchAllSchedules_nonEmptyData_returnCollectionOfSchedules() throws Exception {
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));
        schedules.add(mock(Schedule.class));
        when(scheduleRepository.findAll()).thenReturn(schedules);

        List<Schedule> result = scheduleService.fetchAll();
        assertEquals(3, result.size());
    }

    @Test
    public void fetchPaginatedSchedules() throws Exception {
        Pageable pageable = new PageRequest(0, 2, Sort.Direction.ASC, "startDateTime");
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(mock(Schedule.class));
        scheduleList.add(mock(Schedule.class));
        scheduleList.add(mock(Schedule.class));

        Page<Schedule> movies = new PageImpl<>(scheduleList, pageable, scheduleList.size());
        when(scheduleRepository.findAll(pageable)).thenReturn(movies);

        Page<Schedule> response = scheduleService.fetchPaginatedSchedules(null, null, 0, 2);
        assertEquals(3, response.getTotalElements());
    }

    @Test
    public void fetchAllByDateAndMovieId() throws Exception {
        Movie movie = mock(Movie.class);
        Date date = mock(Date.class);
        List<Schedule> schedules = new ArrayList<>();
        when(scheduleRepository.findAllByDateAndMovie(date, date, 100L)).thenReturn(schedules);

        List<Schedule> result = scheduleService.fetchAllByDateAndMovieId(date, null);
        assertEquals(0, result.size());
    }

    @Test
    public void updateSchedule() throws Exception {
        Schedule schedule = mock(Schedule.class);
        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);
        scheduleService.update(schedule, movie, cinema, new Date(), new Date());
        verify(scheduleRepository, times(1))
                .save(schedule);
    }

    @Test
    public void deleteSchedule() throws Exception {
        Schedule schedule = mock(Schedule.class);
        scheduleService.delete(schedule);
        verify(scheduleRepository, times(1))
                .delete(schedule);
    }

}