package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.ResourceAlreadyExistException;
import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        Schedule schedule = new Schedule();
        schedule.setCinema(cinema);
        schedule.setMovie(movie);

        when(scheduleRepo.save(schedule)).thenReturn(schedule);

        Schedule result = scheduleService.saveNewSchedule(movie, cinema, new Date(), new Date());

        verify(scheduleRepo, times(1)).save(new Schedule());
        Assert.assertEquals(schedule, result);
    }

    @Test
    public void fetchAllSchedule_shouldReturnAllScheduleWithNoSpecificFilter() throws Exception {
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);

        when(scheduleRepo.findAll()).thenReturn(scheduleList);

        List<Schedule> resultList = scheduleService.fetchAllSchedule(null, null);

        verify(scheduleRepo, times(1)).findAll();
        Assert.assertEquals(scheduleList, resultList);
    }

    @Test
    public void fetchAllSchedule_shouldReturnAllScheduleFilteredByDate() throws Exception {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 23:59:00");

        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);

        when(scheduleRepo.findAllByStartDateTimeBetween(eq(startDate), eq(endDate))).thenReturn(scheduleList);

        List<Schedule> resultList = scheduleService.fetchAllSchedule("2017-01-26 09:00:00 +0800", null);

        verify(scheduleRepo, times(1)).findAllByStartDateTimeBetween(startDate, endDate);
        Assert.assertEquals(scheduleList, resultList);
    }

    @Test
    public void fetchAllSchedule_shouldReturnAllScheduleFilteredByMovieId() throws Exception {
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        Long movieId = 200L;

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);

        when(scheduleRepo.findAllByMovieId(eq(movieId))).thenReturn(scheduleList);

        List<Schedule> resultList = scheduleService.fetchAllSchedule(null, 200L);

        verify(scheduleRepo, times(1)).findAllByMovieId(200L);
        Assert.assertEquals(scheduleList, resultList);
    }

    @Test
    public void fetchAllSchedule_shouldReturnAllScheduleFilteredByDateAndMovieId() throws Exception {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 23:59:00");

        Long movieId = 200L;

        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);

        when(scheduleRepo.findAllByStartDateTimeBetweenAndMovieId(eq(startDate), eq(endDate), eq(movieId))).thenReturn(scheduleList);

        List<Schedule> resultList = scheduleService.fetchAllSchedule("2017-01-26 09:00:00 +0800", 200L);

        verify(scheduleRepo, times(1)).findAllByStartDateTimeBetweenAndMovieId(startDate, endDate, 200L);
        Assert.assertEquals(scheduleList, resultList);
    }

    @Test
    public void fetchPaginatedSchedule_shouldReturnPaginatedListWithNoSpecificFilter() throws Exception {
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);

        Page<Schedule> schedulePageList = new PageImpl<>(scheduleList, new PageRequest(0, 2), scheduleList.size());

        when(scheduleRepo.findAll(eq(new PageRequest(0, 2)))).thenReturn(schedulePageList);

        Page<Schedule> resultList = scheduleService.fetchPaginatedSchedule(null, null, 0, 2);

        verify(scheduleRepo, times(1)).findAll(new PageRequest(0, 2));
        Assert.assertEquals(schedulePageList, resultList);
    }

    @Test
    public void fetchPaginatedSchedule_shouldReturnPaginatedListFilteredByDate() throws Exception {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 23:59:00");

        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);

        Page<Schedule> schedulePageList = new PageImpl<>(scheduleList, new PageRequest(0, 2), scheduleList.size());

        when(scheduleRepo.findAllByStartDateTimeBetween(eq(startDate), eq(endDate), eq(new PageRequest(0, 2)))).thenReturn(schedulePageList);

        Page<Schedule> resultList = scheduleService.fetchPaginatedSchedule("2017-01-26 09:00:00 +0800", null, 0, 2);

        verify(scheduleRepo, times(1)).findAllByStartDateTimeBetween(startDate, endDate, new PageRequest(0, 2));
        Assert.assertEquals(schedulePageList, resultList);
    }

    @Test
    public void fetchPaginatedSchedule_shouldReturnPaginatedListFilteredByMovieId() throws Exception {
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        Long movieId = 200L;

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);

        Page<Schedule> schedulePageList = new PageImpl<>(scheduleList, new PageRequest(0, 2), scheduleList.size());

        when(scheduleRepo.findAllByMovieId(eq(movieId), eq(new PageRequest(0, 2)))).thenReturn(schedulePageList);

        Page<Schedule> resultList = scheduleService.fetchPaginatedSchedule(null, 200L, 0, 2);

        verify(scheduleRepo, times(1)).findAllByMovieId(200L, new PageRequest(0, 2));
        Assert.assertEquals(schedulePageList, resultList);
    }

    @Test
    public void fetchPaginatedSchedule_shouldReturnPaginatedListFilteredByDateAndMovieId() throws Exception {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-26 23:59:00");

        Long movieId = 200L;

        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);

        Page<Schedule> schedulePageList = new PageImpl<>(scheduleList, new PageRequest(0, 2), scheduleList.size());

        when(scheduleRepo.findAllByStartDateTimeBetweenAndMovieId(eq(startDate), eq(endDate), eq(movieId), eq(new PageRequest(0, 2)))).thenReturn(schedulePageList);

        Page<Schedule> resultList = scheduleService.fetchPaginatedSchedule("2017-01-26 09:00:00 +0800", 200L, 0, 2);

        verify(scheduleRepo, times(1)).findAllByStartDateTimeBetweenAndMovieId(startDate, endDate, 200L, new PageRequest(0, 2));
        Assert.assertEquals(schedulePageList, resultList);
    }


    @Test
    public void updateSchedule_shouldUpdateTheExistingSchedule() throws Exception {
        Schedule schedule = new Schedule();
        Movie movie = new Movie();
        Cinema cinema = new Cinema();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);

        when(scheduleRepo.save(eq(schedule))).thenReturn(schedule);

        Schedule result = scheduleService.updateSchedule(schedule, movie, cinema, new Date(), new Date());

        verify(scheduleRepo, times(1)).save(eq(schedule));
        Assert.assertEquals(schedule, result);
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
