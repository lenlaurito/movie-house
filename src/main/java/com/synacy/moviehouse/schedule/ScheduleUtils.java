package com.synacy.moviehouse.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by steven on 5/16/17.
 */
@Service
public class ScheduleUtils {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Date dateStringParser(String dateInString) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateInString);
        return date;
    }
    public Boolean isValidEndDateTime(int duration, Date startTime, Date endTime){
        int differenceInMinutes = ((int) (endTime.getTime() - startTime.getTime()))/60000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(differenceInMinutes >= duration && dateFormat.format(startTime).equals(dateFormat.format(endTime))) return true;
        else return false;
    }

    public Boolean isDateOverlapping(Schedule schedule){
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
