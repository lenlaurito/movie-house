package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.exception.InvalidDataPassedException;
import com.synacy.moviehouse.exception.NoContentFoundException;
import com.synacy.moviehouse.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by steven on 5/12/17.
 */
@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule fetchById(Long id) {
        Schedule schedule = scheduleRepository.findOne(id);

        if(schedule == null)
            throw new NoContentFoundException("Not content found");
        else
            return schedule;
    }

    public List<Schedule> fetchAll(String dateInString) throws ParseException {
        List<Schedule> scheduleList;

        if(dateInString != null)
           scheduleList = scheduleRepository.findAllByDate(dateFormatter(dateInString));
        else
            scheduleList = (List)scheduleRepository.findAll();

        if(scheduleList.size() < 1)
            throw new NoContentFoundException("Not content found");
        else
            return scheduleList;
    }

    public Page<Schedule> fetchAllPaginated(String dateInString, Integer offset, Integer max) throws ParseException {
        Page<Schedule> page;

        if(dateInString != null)
            page = scheduleRepository.findAllByDate(dateFormatter(dateInString),new PageRequest(offset, max));
        else
            page = scheduleRepository.findAll(new PageRequest(offset, max));

        if(page.getTotalPages() < 1)
            throw new NoContentFoundException("Not content found");
        else
            return page;
    }

    public Schedule createSchedule(Movie movie, Cinema cinema, Date startDateTime, Date endDateTime) throws ParseException {

        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        schedule.setDate(dateFormat.parse(dateFormat.format(startDateTime)));
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        if(isValidEndDateTime(schedule.getMovie().getDuration(),schedule.getStartDateTime(),schedule.getEndDateTime())
                && !isDateOverlapping(schedule))
            return  scheduleRepository.save(schedule);
        else
            throw new InvalidDataPassedException("Invalid Date Input");
    }

    public Schedule updateSchedule(Schedule schedule, Movie movie, Cinema cinema, Date startDateTime, Date endDateTime){

        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);

        if(isValidEndDateTime(schedule.getMovie().getDuration(),schedule.getStartDateTime(),schedule.getEndDateTime())
                && !isDateOverlapping(schedule))
            return  scheduleRepository.save(schedule);
        else
            throw new InvalidDataPassedException("Invalid Date Input");
    }

    public void deleteSchedule(Schedule schedule){
        scheduleRepository.delete(schedule);
    }

    private Date dateFormatter(String dateInString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateInString);
    }

    private Boolean isValidEndDateTime(int duration, Date startTime, Date endTime){
        int differenceInMinutes = ((int) (endTime.getTime() - startTime.getTime()))/60000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(differenceInMinutes >= duration && dateFormat.format(startTime).equals(dateFormat.format(endTime))) return true;
        else return false;
    }

    private Boolean isDateOverlapping(Schedule schedule){
        List<Schedule> scheduleList = (List)scheduleRepository.findAll();
        for(Schedule scheduleInList : scheduleList){
            if(scheduleInList.getCinema().getId() == schedule.getCinema().getId()){
                if((schedule.getStartDateTime().after(scheduleInList.getStartDateTime())
                        && schedule.getStartDateTime().before(scheduleInList.getEndDateTime()))
                        || (schedule.getEndDateTime().after(scheduleInList.getStartDateTime())
                        && schedule.getEndDateTime().before(scheduleInList.getEndDateTime()))
                        || schedule.getStartDateTime().equals(scheduleInList.getStartDateTime())
                        || schedule.getEndDateTime().equals(scheduleInList.getEndDateTime())){
                    return true;
                }
            }
        }
        return false;
    }

}
