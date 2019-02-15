package com.synacy.moviehouse.movie;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

@Entity
@Data
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence")
	@SequenceGenerator(name = "movie_sequence", sequenceName = "movie_sequence", allocationSize = 1)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String genre;

	@NotNull
	private Integer duration;

	private String description;

}
