package com.synacy.moviehouse.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@EqualsAndHashCode(of = "startDateTime")
@Entity
@JsonSerialize(using = ScheduleSerializer.class)
public class Schedule {

	@Id @GeneratedValue
	private Long id;

	@NotNull @OneToOne(fetch = FetchType.LAZY) @Setter
	private Movie movie;

	@NotNull @OneToOne(fetch = FetchType.LAZY) @Setter
	private Cinema cinema;

	@NotNull @Setter
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date startDateTime;

	@NotNull @Setter
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date endDateTime;

}
