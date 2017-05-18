package com.synacy.moviehouse.movie;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
public class Movie {

	@Id
	@GeneratedValue
	private Long id;

	@Setter @NotNull
	private String name;

	@Setter @NotNull
	@Enumerated(EnumType.STRING)
	private Genre genre;

	@Setter @NotNull
	private Integer duration;

	@Setter @Size(max=2000)
	private String description;

}
