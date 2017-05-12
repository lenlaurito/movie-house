package com.synacy.moviehouse.schedule;

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
public class Schedule {

	@Id @GeneratedValue
	private Long id;

	@Setter	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Movie movie;

	@Setter @NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Cinema cinema;

	@Setter @NotNull
	private Date startDateTime;

	@Setter @NotNull
	private Date endDateTime;

}
