package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    ScheduleController scheduleController;

    @Test
    public void fetchSchedule() throws Exception {
        scheduleController.fetchSchedule(100L);
        verify(scheduleService, times(1))
                .fetchById(100L);
    }

    @Test
    public void fetchAllSchedules() throws Exception {
        List<Schedule> schedules = new ArrayList<>();
        when(scheduleService.fetchAll()).thenReturn(schedules);
        scheduleController.fetchAllSchedules(null, null, null, null);
        verify(scheduleService, times(1))
                .fetchAll();
    }

    @Test
    public void createNewSchedule() throws Exception {
        Schedule newSchedule = mock(Schedule.class);
        scheduleController.createNewSchedule(newSchedule);
        verify(scheduleService, times(1))
                .create(newSchedule);
    }

    @Test
    public void updateSchedule() throws Exception {
        Schedule schedule = mock(Schedule.class);
        Movie movie = mock(Movie.class);
        Cinema cinema = mock(Cinema.class);
        Date date = mock(Date.class);
        when(scheduleService.fetchById(100L)).thenReturn(schedule);
        when(schedule.getMovie()).thenReturn(movie);
        when(schedule.getCinema()).thenReturn(cinema);
        when(schedule.getStartDateTime()).thenReturn(date);
        when(schedule.getEndDateTime()).thenReturn(date);

        scheduleController.updateSchedule(100L, schedule);

        verify(scheduleService, times(1))
                .update(schedule, movie, cinema, date, date);
    }

    @Test
    public void deleteSchedule() throws Exception {
        Schedule schedule = mock(Schedule.class);
        when(scheduleService.fetchById(100L)).thenReturn(schedule);

        scheduleController.deleteSchedule(100L);

        verify(scheduleService, times(1))
                .delete(schedule);
    }

}