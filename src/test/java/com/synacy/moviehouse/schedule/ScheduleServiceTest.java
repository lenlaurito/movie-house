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
import java.text.SimpleDateFormat;
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

        schedules.add(new Schedule());
        schedules.add(new Schedule());

        when(scheduleRepository.findAll(pageRequest)).thenReturn(pageOjb);
        when(pageOjb.getContent()).thenReturn(schedules);
        when(schedules.size()).thenReturn(2);

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

        schedules.add(new Schedule());
        schedules.add(new Schedule());

        when(scheduleRepository.findAllWithinScheduleAndNameContaining(date, movieName, pageRequest))
                .thenReturn(pageOjb);
        when(pageOjb.getContent()).thenReturn(schedules);
        when(schedules.size()).thenReturn(2);

        scheduleService.fetchAllSchedules(pageRequest, date, movieName);

        int expectedInvoccations = 1;

        verify(scheduleRepository, times(expectedInvoccations)).findAllWithinScheduleAndNameContaining(
                ArgumentMatchers.eq(date),
                ArgumentMatchers.eq(movieName),
                ArgumentMatchers.eq(pageRequest));
    }

    @Test
    public void createSchedule_nonOverLappingScheduleDates_shouldSaveMovie() throws Exception {
        Date start = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01");
        Date end = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-31");
        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        when(scheduleRepository.isScheduleAvailable(start, end, cinema.getId())).thenReturn(true);

        scheduleService.createSchedule(start, end, movie, cinema);

        int expectedInvocations = 1;

        verify(scheduleRepository, times(1)).save(Mockito.any(Schedule.class));
    }

    @Test(expected = ScheduleNotAvailableException.class)
    public void createSchedule_overLappingScheduleDates_shouldThrowException() throws Exception {
        Date start = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01");
        Date end = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-31");
        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        Schedule schedule = new Schedule();
        schedule.setStartDateTime(start);
        schedule.setEndDateTime(end);
        schedule.setMovie(movie);
        schedule.setCinema(cinema);

        when(scheduleRepository.isScheduleAvailable(start, end, cinema.getId())).thenReturn(false);

        Schedule createdSchedule = scheduleService.createSchedule(start, end, movie, cinema);

        verify(scheduleRepository, times(1))
                .save(ArgumentMatchers.eq(Mockito.any(Schedule.class)));

        assert createdSchedule.getStartDateTime().equals(start);
        assert createdSchedule.getEndDateTime().equals(end);
        assert createdSchedule.getMovie().equals(movie);
        assert createdSchedule.getCinema().equals(cinema);
    }

    @Test
    public void fetchSscheduleById_exist_shouldReturnSchedule() throws Exception {
        Long idToFind = new Long(1);

        Date start = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01");
        Date end = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-31");
        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        Schedule schedule = new Schedule();
        schedule.setId(idToFind);
        schedule.setStartDateTime(start);
        schedule.setEndDateTime(end);
        schedule.setMovie(movie);
        schedule.setCinema(cinema);

        when(scheduleRepository.findOne(idToFind)).thenReturn(schedule);

        Schedule fetchedSchedule = scheduleService.fetchScheduleById(idToFind);

        int expectedInvocations = 1;

        verify(scheduleRepository, times(expectedInvocations)).findOne(idToFind);

        assert fetchedSchedule.getId().equals(idToFind);
        assert fetchedSchedule.getStartDateTime().equals(start);
        assert fetchedSchedule.getEndDateTime().equals(end);
        assert fetchedSchedule.getMovie().equals(movie);
        assert fetchedSchedule.getCinema().equals(cinema);
    }

    @Test(expected = NoResultException.class)
    public void fetchSscheduleById_notExist_shouldThrowException() throws Exception {
        Long idToFind = new Long(1);

        scheduleService.fetchScheduleById(idToFind);
    }

    @Test
    public void updateSchedule_exist_shouldSaveSchedule() throws Exception {
        Long idToFind = new Long(1);

        Date start = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01");
        Date end = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-31");
        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        Schedule schedule = new Schedule();
        schedule.setId(idToFind);
        schedule.setStartDateTime(start);

        when(scheduleRepository.isScheduleAvailable(start, end, cinema.getId(), idToFind)).thenReturn(true);
        when(scheduleRepository.findOne(idToFind)).thenReturn(schedule);
        when(scheduleRepository.save(schedule)).thenReturn(schedule);

        Schedule savedSchedule = scheduleService.updateSchedule(idToFind, start, end, movie, cinema);

        int expectedInvocations = 1;

        verify(scheduleRepository, times(expectedInvocations)).findOne(idToFind);
        verify(scheduleRepository, times(expectedInvocations)).save(schedule);

        assert savedSchedule.getId().equals(idToFind);
        assert savedSchedule.getStartDateTime().equals(start);
        assert savedSchedule.getEndDateTime().equals(end);
        assert savedSchedule.getMovie().equals(movie);
        assert savedSchedule.getCinema().equals(cinema);
    }

    @Test
    public void deleteScheduleById_existing_shouldDeleteSchedule() throws Exception {
        Long idToFind = new Long(1);

        Schedule schedule = new Schedule();

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