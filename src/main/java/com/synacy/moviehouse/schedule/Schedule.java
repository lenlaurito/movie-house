package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.movie.Movie;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Schedule {

	@Id @GeneratedValue
	private Long id;

	@NotNull @OneToOne(fetch = FetchType.LAZY)
	private Movie movie;

//	@NotNull @OneToOne(fetch = FetchType.LAZY)
//	private Cinema cinema;

	@NotNull
	private Date startDateTime;

	@NotNull
	private Date endDateTime;

}
