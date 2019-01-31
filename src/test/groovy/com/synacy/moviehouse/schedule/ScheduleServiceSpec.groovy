package com.synacy.moviehouse.schedule

import com.synacy.moviehouse.exception.ScheduleAlreadyExistsException
import com.synacy.moviehouse.exception.ScheduleNotFoundException
import com.synacy.moviehouse.movie.Movie
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

    def "updateSchedule should update content of schedule"() {
        given:
        Schedule expectedSchedule = Mock(Schedule)
        Date start = Mock(Date)
        Date end = Mock(Date)
        Movie movie = Mock(Movie)

        expectedSchedule.id >> 1
        expectedSchedule.startDateTime >> start
        expectedSchedule.endDateTime >> end
        expectedSchedule.movie >> movie

        scheduleRepository.findById(1) >> Optional.of(expectedSchedule)

        when:
        scheduleService.updateSchedule(expectedSchedule, 1)

        then:
        1 == expectedSchedule.id
        start == expectedSchedule.startDateTime
        end == expectedSchedule.endDateTime
        movie == expectedSchedule.movie
    }

    def "updateSchedule should throw ScheduleNotFoundException if given schedule does not exist"() {
        given:
        Schedule mockSchedule = Mock(Schedule)

        mockSchedule.getId() >> 1

        scheduleRepository.findById(mockSchedule.getId()) >> Optional.empty()

        when:
        scheduleService.updateSchedule(mockSchedule, 1)

        then:
        thrown(ScheduleNotFoundException)
    }
}
