package com.synacy.moviehouse.movie;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Movie {

	@Id @NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String genre;

	@NotNull
	private Integer duration;

	private String description;

}
