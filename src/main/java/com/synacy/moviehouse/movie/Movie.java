package com.synacy.moviehouse.movie;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
@JsonSerialize(using = MovieSerializer.class)
public class Movie {

	@Id @NotNull @GeneratedValue
	private Long id;

	@NotNull @Setter
	private String name;

	@NotNull @Setter
	@Enumerated(EnumType.STRING)
	private MovieGenre genre;

	@NotNull @Setter
	private Integer duration;

	@Setter
	private String description;

}
