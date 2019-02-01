package com.synacy.moviehouse.schedule

import spock.lang.Specification

class ScheduleControllerSpec extends Specification {

    ScheduleController scheduleController

    ScheduleService scheduleService = Mock(ScheduleService)

    void setup() {
        scheduleController = new ScheduleController()
    }

    void cleanup() {}

}
