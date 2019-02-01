package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List <Schedule> findByMovie(Movie movie);

    @Query("SELECT * FROM Schedule s WHERE NOT (s.startDateTime > ?1 OR s.endDateTime < ?2)")
    List<Schedule> findByDay(Date start, Date end);

    //@Query("SELECT sched FROM Schedule where sched.startDateTime < :date AND sched.endDateTime > :date")
    //List<Schedule> findScheduleByDay(@Param("date") Date date);
}
