package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List <Schedule> findByMovie(Movie movie);

    //@Query("SELECT sched FROM Schedule where sched.startDateTime < :date AND sched.endDateTime > :date")
    //List<Schedule> findScheduleByDay(@Param("date") Date date);
}
