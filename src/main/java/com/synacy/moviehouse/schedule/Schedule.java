package com.synacy.moviehouse.schedule;

import com.synacy.moviehouse.movie.Movie;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull @OneToOne(fetch = FetchType.LAZY)
	private Movie movie;

	@NotNull
	private Date startDateTime;

	@NotNull
	private Date endDateTime;

}
