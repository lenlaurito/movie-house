package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by michael on 5/15/17.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    @Mock
    ScheduleRepository scheduleRepository;

    @Before
    public void setUp() throws Exception {
        scheduleService = new ScheduleService();

        scheduleService.setScheduleRepository(scheduleRepository);
    }

    @Test
    public void fetchAllSchedules_withNoFilter_shouldFindAll() throws Exception {
        int page = 0;
        int size = 10;

        String filter = null;

        Page pageOjb = mock(Page.class);
        PageRequest pageRequest = mock(PageRequest.class);
        List<Schedule> schedules = mock(List.class);

        when(scheduleRepository.findAll(pageRequest)).thenReturn(pageOjb);
        when(pageOjb.getContent()).thenReturn(schedules);

        scheduleService.fetchAllSchedules(pageRequest, null, null);

        int expectedInvoccations = 1;

        verify(scheduleRepository, times(expectedInvoccations)).findAll(ArgumentMatchers.eq(pageRequest));
    }

    @Test
    public void fetchAllSchedules_withFilterType_shouldFindAllByTypeContaining() throws Exception {
        int page = 0;
        int size = 10;

        String date = "2017-01-01";
        String movieName = "matrix";

        Page pageOjb = mock(Page.class);
        PageRequest pageRequest = mock(PageRequest.class);
        List<Schedule> schedules = mock(List.class);

        when(scheduleRepository.findAllWithinScheduleAndNameContaining(date, movieName, pageRequest))
                .thenReturn(pageOjb);
        when(pageOjb.getContent()).thenReturn(schedules);

        scheduleService.fetchAllSchedules(pageRequest, date, movieName);

        int expectedInvoccations = 1;

        verify(scheduleRepository, times(expectedInvoccations)).findAllWithinScheduleAndNameContaining(
                ArgumentMatchers.eq(date),
                ArgumentMatchers.eq(movieName),
                ArgumentMatchers.eq(pageRequest));
    }

    @Test
    public void createSchedule() throws Exception {
        scheduleService.createSchedule(mock(Date.class), mock(Date.class), mock(Movie.class), mock(Cinema.class));

        int expectedInvocations = 1;

        verify(scheduleRepository, times(1)).save(Mockito.any(Schedule.class));
    }

    @Test
    public void fetchSscheduleById_exist_shouldReturnSchedule() throws Exception {
        Long idToFind = new Long(1);

        Schedule schedule = mock(Schedule.class);

        when(scheduleRepository.findOne(idToFind)).thenReturn(schedule);

        scheduleService.fetchScheduleById(idToFind);

        int expectedInvocations = 1;

        verify(scheduleRepository, times(expectedInvocations)).findOne(idToFind);
    }

    @Test(expected = NoResultException.class)
    public void fetchSscheduleById_notExist_shouldThrowException() throws Exception {
        Long idToFind = new Long(1);

        scheduleService.fetchScheduleById(idToFind);
    }

    @Test
    public void updateSchedule_exist_shouldSaveSchedule() throws Exception {
        Long idToFind = new Long(1);

        Schedule schedule = mock(Schedule.class);

        when(scheduleRepository.findOne(idToFind)).thenReturn(schedule);

        scheduleService.updateSchedule(idToFind, mock(Date.class), mock(Date.class),
                mock(Movie.class), mock(Cinema.class));

        int expectedInvocations = 1;

        verify(scheduleRepository, times(expectedInvocations)).findOne(idToFind);
        verify(scheduleRepository, times(expectedInvocations)).save(schedule);
    }

    @Test
    public void deleteScheduleById_existing_shouldDeleteSchedule() throws Exception {
        Long idToFind = new Long(1);

        Schedule schedule = mock(Schedule.class);

        when(scheduleRepository.findOne(idToFind)).thenReturn(schedule);

        scheduleService.deleteScheduleById(idToFind);

        int expectedInvocations = 1;

        verify(scheduleRepository, times(expectedInvocations)).findOne(idToFind);
        verify(scheduleRepository, times(expectedInvocations)).delete(schedule);
    }

    @Test(expected = NoResultException.class)
    public void deleteScheduleById_notExisting_shouldThrowException() throws Exception {
        Long idToFind = new Long(1);

        scheduleService.deleteScheduleById(idToFind);
    }
}