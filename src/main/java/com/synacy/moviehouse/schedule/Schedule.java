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
@EqualsAndHashCode(of = {"id"})
@Entity
@JsonSerialize(using = ScheduleSerializer.class)
public class Schedule {

	@Id @GeneratedValue
	private Long id;

	@Setter @NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Movie movie;

	@Setter @NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Cinema cinema;

	@Setter @NotNull
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Manila")
	private Date startDateTime;

	@Setter @NotNull
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Manila")
	private Date endDateTime;

}
