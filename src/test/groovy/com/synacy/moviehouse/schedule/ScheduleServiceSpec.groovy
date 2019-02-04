package com.synacy.moviehouse.schedule

import com.synacy.moviehouse.exception.ScheduleAlreadyExistsException
import com.synacy.moviehouse.exception.ScheduleConflictException
import com.synacy.moviehouse.exception.ScheduleNotFoundException
import com.synacy.moviehouse.movie.Movie
import com.synacy.moviehouse.movie.MovieRepository
import spock.lang.Specification

import java.text.SimpleDateFormat

class ScheduleServiceSpec extends Specification {

    ScheduleService scheduleService;

    ScheduleRepository scheduleRepository = Mock(ScheduleRepository)
    MovieRepository movieRepository = Mock(MovieRepository)

    void setup() {
        scheduleService = new ScheduleService(scheduleRepository, movieRepository)
    }

    void cleanup() {}

    def "createNewSchedule should return Schedule object"() {
        given:
        Schedule expectedSchedule = buildSchedule()

        scheduleRepository.save(_ as Schedule) >> expectedSchedule
        scheduleRepository.findById(expectedSchedule.getId()) >> Optional.empty()

        when:
        Schedule actualMovie = scheduleService.createNewSchedule(expectedSchedule)

        then:
        expectedSchedule == actualMovie
    }

    def "createNewSchedule should throw ScheduleAlreadyExistsException if given schedule already exists"() {
        given:
        Schedule mockSchedule = buildSchedule()

        scheduleRepository.findById(mockSchedule.id) >> Optional.of(mockSchedule)

        when:
        scheduleService.createNewSchedule(mockSchedule)

        then:
        thrown(ScheduleAlreadyExistsException)
    }

    def "createNewSchedule should throw ScheduleConflictException if the time difference between startDateTime and endDateTime is less than the movie duration"() {
        given:
        Schedule mockSchedule = buildConflictchedule()

        scheduleRepository.findById(1) >> Optional.empty()

        when:
        scheduleService.createNewSchedule(mockSchedule)

        then:
        thrown(ScheduleConflictException)
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

        scheduleRepository.findAll() >> expectedSchedules

        when:
        List <Schedule> actualSchedules = scheduleService.getAllSchedules()

        then:
        expectedSchedules == actualSchedules
    }

    /*def "getSchedulesByMovie should return list of schedules based on given movie"() {
        given:
        List <Schedule> expectedSchedules = buildSchedules()
        Movie firstMovie = Mock(Movie)
        Movie secondMovie = Mock(Movie)
        firstMovie.id >> 1
        secondMovie.id >> 2

        expectedSchedules[0].movie >> firstMovie
        expectedSchedules[1].movie >> firstMovie
        expectedSchedules[2].movie >> secondMovie

        scheduleRepository.findByMovie(firstMovie) >> [expectedSchedules[0], expectedSchedules[1]]

        when:
        List <Schedule> schedulesByMovie = scheduleService.getSchedulesByMovie(1)

        then:
        schedulesByMovie.each { Schedule sched ->
            assert firstMovie.id == sched.movie
        }
    }*/

    /*def "getSchedulesByDay should return list of schedules based on given day"() {
        given:
        List <Schedule> expectedSchedules = buildSchedules()

        SimpleDateFormat sdt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date date = sdt.parse("01-03-2019 10:00:00");

        scheduleRepository.findScheduleByDay(date) >> [expectedSchedules[0]]

        when:
        List <Schedule> schedulesByDay = scheduleService.getSchedulesByDay(date)

        then:
        schedulesByDay.each { Schedule sched ->
            assert expectedSchedules[0] == sched
        }
    }*/

    def "getScheduleById should return schedule object based on given id"() {
        given:
        Schedule expectedSched = Mock(Schedule)
        expectedSched.id >> 1

        scheduleRepository.findById(expectedSched.id) >> Optional.of(expectedSched)

        when:
        Schedule actualSched = scheduleService.getScheduleById(1)

        then:
        expectedSched == actualSched
    }

    List <Schedule> buildSchedules() {
        Schedule firstSched = Mock(Schedule)
        Schedule secondSched = Mock(Schedule)
        Schedule thirdSched = Mock(Schedule)

        firstSched.startDateTime >> "01-01-2019 08:00:00"
        firstSched.endDateTime >> "01-07-2019 08:00:00"
        secondSched.startDateTime >> "01-07-2019 09:00:00"
        secondSched.endDateTime >> "01-13-2019 09:00:00"
        thirdSched.startDateTime >> "01-13-2019 10:00:00"
        thirdSched.endDateTime >> "01-19-2019 10:00:00"

        return [firstSched, secondSched, thirdSched]
    }

    Schedule buildSchedule() {
        Schedule mockSchedule = Mock(Schedule)
        Movie mockMovie = Mock(Movie)
        mockMovie.getDuration() >> 90

        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        mockSchedule.getId() >> 1
        mockSchedule.getEndDateTime() >> sdt.parse("2019-01-01 10:00:00")
        mockSchedule.getStartDateTime() >> sdt.parse("2019-01-01 08:00:00")
        mockSchedule.getMovie() >> mockMovie

        return mockSchedule
    }

    Schedule buildConflictchedule() {
        Schedule mockSchedule = Mock(Schedule)
        Movie mockMovie = Mock(Movie)
        mockMovie.duration >> 90

        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        mockSchedule.id >> 1
        mockSchedule.endDateTime >> sdt.parse("2019-01-01 8:30:00")
        mockSchedule.startDateTime >> sdt.parse("2019-01-01 08:00:00")
        mockSchedule.movie >> mockMovie

        return mockSchedule
    }
}
