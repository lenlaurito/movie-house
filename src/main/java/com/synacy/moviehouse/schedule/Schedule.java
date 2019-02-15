package com.synacy.moviehouse.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synacy.moviehouse.movie.Movie;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_sequence")
	@SequenceGenerator(name = "schedule_sequence", sequenceName = "schedule_sequence", allocationSize = 1)
	private Long id;

	@NotNull @OneToOne(fetch = FetchType.EAGER)
	private Movie movie;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDateTime;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDateTime;

}
