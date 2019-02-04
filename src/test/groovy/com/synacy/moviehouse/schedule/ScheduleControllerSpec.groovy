package com.synacy.moviehouse.schedule

import com.synacy.moviehouse.movie.Movie
import spock.lang.Specification

import java.text.SimpleDateFormat

class ScheduleControllerSpec extends Specification {

    ScheduleController scheduleController

    ScheduleService scheduleService = Mock(ScheduleService)

    void setup() {
        scheduleController = new ScheduleController(scheduleService)
    }

    void cleanup() {}

    def "createNewMovieSchedule should return the newly created Schedule"() {
        given:
        Schedule expectedSched = Mock(Schedule)

        scheduleService.createNewSchedule(expectedSched) >> expectedSched

        when:
        Schedule actualSched = scheduleController.createNewSchedule(expectedSched)

        then:
        expectedSched == actualSched
    }

    def "updateSchedule should should update the contents of Schedule of the given id"() {
        given:
        Schedule expectedSched = Mock(Schedule)
        List <Date> expectedDates = buildStartAndEndDateTime()
        Movie mockMovie = Mock(Movie)

        expectedSched.id >> 1
        expectedSched.startDateTime >> expectedDates[0]
        expectedSched.endDateTime >> expectedDates[1]
        expectedSched.movie >> mockMovie

        when:
        scheduleController.updateSchedule(expectedSched, 1)

        then:
        1 == expectedSched.id
        expectedDates[0] == expectedSched.startDateTime
        expectedDates[1] == expectedSched.endDateTime
        mockMovie == expectedSched.movie
    }

    def "getScheduleById should return Schedule based on the given id"() {
        given:
        Schedule expectedSchedule = Mock(Schedule)

        scheduleService.getScheduleById(1) >> expectedSchedule

        when:
        Schedule actualSched = scheduleController.getScheduleById(1)

        then:
        expectedSchedule == actualSched
    }

    def "getSchedules should return list of all schedules if there are no parameters"() {
        given:
        List <Schedule> expectedSched = buildSchedules()

        scheduleService.getAllSchedules() >> expectedSched

        when:
        List <Schedule> actualSched = scheduleController.getSchedules(Optional.empty(), null, null)

        then:
        expectedSched == actualSched
    }

    def "getSchedules should return list of schedules based given movie"() {
        given:
        List <Schedule> schedules = buildSchedules()

        scheduleService.getSchedulesByMovie(1) >> [schedules[0]]

        when:
        List <Schedule> schedByMovie = scheduleController.getSchedules(Optional.of(1L), null, null)

        then:
        schedByMovie.each { Schedule sched ->
            assert schedules[0].movie == sched.movie
        }
    }

    List <Date> buildStartAndEndDateTime() {
        SimpleDateFormat sdt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        Date start = sdt.parse("01-01-2019 08:00:00")
        Date end = sdt.parse("01-07-2019 08:00:00")

        return [start, end]
    }

    List <Schedule> buildSchedules() {
        Schedule firstSched = Mock(Schedule)
        Schedule secondSched = Mock(Schedule)
        Schedule thirdSched = Mock(Schedule)

        Movie firstMovie = Mock(Movie)
        firstMovie.id >> 1
        Movie secondMovie = Mock(Movie)
        secondMovie.id >> 2
        Movie thirdMovie = Mock(Movie)
        thirdMovie.id >> 3

        firstSched.movie >> firstMovie
        secondSched.movie >> secondMovie
        thirdSched.movie >> thirdMovie

        return [firstSched, secondSched, thirdSched]
    }
}
