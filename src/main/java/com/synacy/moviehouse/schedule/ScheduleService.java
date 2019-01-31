package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.exception.ScheduleAlreadyExistsException;
import com.synacy.moviehouse.exception.ScheduleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule createNewSchedule(Schedule schedule) throws ScheduleAlreadyExistsException {
        Schedule newSchedule = validateIfScheduleAlreadyExists(schedule);

        return scheduleRepository.save(newSchedule);
    }

    public void updateSchedule(Schedule schedule, long id) throws ScheduleNotFoundException {
        Schedule newSchedule = validateIfScheduleDoesNotExist(schedule, id);

        scheduleRepository.save(newSchedule);
    }

    public Schedule validateIfScheduleAlreadyExists(Schedule schedule) throws ScheduleAlreadyExistsException {
        Optional <Schedule> optionalSchedule = scheduleRepository.findById(schedule.getId());

        if (optionalSchedule.isPresent())
            throw new ScheduleAlreadyExistsException();

        return schedule;
    }

    public Schedule validateIfScheduleDoesNotExist(Schedule schedule, long id) throws ScheduleNotFoundException {
        Optional <Schedule> optionalSchedule = scheduleRepository.findById(id);

        if (optionalSchedule.isEmpty())
            throw new ScheduleNotFoundException();

        return schedule;
    }
}
