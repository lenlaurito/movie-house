package com.synacy.moviehouse.schedule

import com.synacy.moviehouse.exception.ScheduleAlreadyExistsException
import spock.lang.Specification

class ScheduleServiceSpec extends Specification {

    ScheduleService scheduleService;

    ScheduleRepository scheduleRepository = Mock(ScheduleRepository);

    void setup() {
        scheduleService = new ScheduleService(scheduleRepository);
    }

    void cleanup() {}

    def "createNewSchedule should return Schedule object"() {
        given:
        Schedule expectedSchedule = Mock(Schedule)

        expectedSchedule.getId() >> 1

        scheduleRepository.save(_ as Schedule) >> expectedSchedule
        scheduleRepository.findById(expectedSchedule.getId()) >> Optional.empty()

        when:
        Schedule actualMovie = scheduleService.createNewSchedule(expectedSchedule)

        then:
        expectedSchedule == actualMovie
    }

    def "createNewSchedule should throw ScheduleAlreadyExistsException if given schedule already exists"() {
        given:
        Schedule mockSchedule = Mock(Schedule)
        mockSchedule.id >> 1

        scheduleRepository.findById(mockSchedule.id) >> Optional.of(mockSchedule)

        when:
        scheduleService.createNewSchedule(mockSchedule)

        then:
        thrown(ScheduleAlreadyExistsException)
    }
}
