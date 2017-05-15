package com.synacy.moviehouse.movie;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Movie {

	@Id @Setter @Getter @NotNull
	private Long id;

	@Setter @Getter @NotNull
	private String name;

	@Setter @Getter @NotNull
	private String genre;

	@Setter @Getter @NotNull
	private Integer duration;

	@Setter @Getter
	private String description;

}
