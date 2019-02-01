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

    List <Date> buildStartAndEndDateTime() {
        SimpleDateFormat sdt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        Date start = sdt.parse("01-01-2019 08:00:00")
        Date end = sdt.parse("01-07-2019 08:00:00")

        return [start, end]
    }
}
