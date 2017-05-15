package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.cinema.Cinema;
import com.synacy.moviehouse.movie.Movie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Schedule {

	@Id @GeneratedValue @Setter @Getter
	private Long id;

	@NotNull @OneToOne(fetch = FetchType.LAZY) @Setter @Getter
	private Movie movie;

	@NotNull @OneToOne(fetch = FetchType.LAZY) @Setter @Getter
	private Cinema cinema;

	@NotNull @Setter @Getter
	private Date startDateTime;

	@NotNull @Setter @Getter
	private Date endDateTime;

}
