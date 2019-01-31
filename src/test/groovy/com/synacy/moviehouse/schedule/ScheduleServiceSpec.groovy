package com.synacy.moviehouse.schedule

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

        scheduleRepository.save(_ as Schedule) >> expectedSchedule

        when:
        Schedule actualSchedule = scheduleService.createNewSchedule(expectedSchedule)

        then:
        expectedSchedule == actualSchedule
    }
}
