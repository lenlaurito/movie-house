package com.synacy.moviehouse.schedule

import spock.lang.Specification

class ScheduleControllerSpec extends Specification {

    ScheduleController scheduleController

    ScheduleService scheduleService = Mock(ScheduleService)

    void setup() {
        scheduleController = new ScheduleController()
    }

    void cleanup() {}

    def "createNewMovieSchedule should return the newly created Schedule"() {
        given:
        Schedule expectedSched = Mock(Schedule)

        scheduleService.createNewSchedule(_ as Schedule) >> expectedSched

        when:
        Schedule actualSched = scheduleController.createNewSchedule(expectedSched)

        then:
        expectedSched == actualSched
    }
}
