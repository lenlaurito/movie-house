package com.synacy.moviehouse.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
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

	@NotNull @Setter @Getter @OneToOne(fetch = FetchType.LAZY)
	private Movie movie;

	@NotNull @Setter @Getter @OneToOne(fetch = FetchType.LAZY)
	private Cinema cinema;

	@NotNull @Setter @Getter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDateTime;

	@NotNull @Setter @Getter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDateTime;

}
