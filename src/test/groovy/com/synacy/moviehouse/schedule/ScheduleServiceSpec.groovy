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

    def "getAllSchedules should return list of schedules"() {
        given:
        List <Schedule> expectedSchedules = buildSchedules()

        scheduleService.findAll() >> expectedSchedules

        when:
        List <Schedule> actualSchedules = scheduleService.getAllSchedules()

        then:
        expectedSchedules == actualSchedules
    }

    /*def "getSchedulesByMovie should return list of schedules based on given movie"() {
        given:
        List <Schedule> expectedSchedules = buildSchedules()
        Movie firstMovie = Mock(movie)
        Movie secondMovie = Mock(movie)

        expectedSchedules[0].movie >> firstMovie
        expectedSchedules[1].movie >> firstMovie
        expectedSchedules[2].movie >> secondMovie

        scheduleRepository.findByMovie(firstMovie) >> [expectedSchedules[0], expectedSchedules[1]]

        when:
        List <Schedule> schedulesByMovie = scheduleService.getSchedulesByMovie(firstMovie)

        then:
        schedulesByMovie.each { Schedule sched ->
            assert firstMovie == sched.movie
        }
    }*/

    List <Schedule> buildSchedules() {
        Schedule firstSched = Mock(Schedule)
        Schedule secondSched = Mock(Schedule)
        Schedule thirdSched = Mock(Schedule)

        return
    }
}
