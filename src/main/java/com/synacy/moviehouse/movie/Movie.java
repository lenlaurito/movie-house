package com.synacy.moviehouse.movie;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
public class Movie {

	@Id @GeneratedValue
	@NotNull
	@Setter
	private Long id;

	@Setter @NotNull
	private String name;

	@Setter @NotNull
	private String genre;

	@Setter @NotNull
	private Integer duration;

	@Setter
	private String description;
}
