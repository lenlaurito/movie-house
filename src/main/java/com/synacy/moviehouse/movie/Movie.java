package com.synacy.moviehouse.movie;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(of = {"id"})
@Entity
@JsonSerialize(using = MovieSerializer.class)
public class Movie {

	@Id @NotNull @GeneratedValue
	private Long id;

	@NotNull
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private GenreType genre;

	@NotNull
	private Integer duration;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GenreType getGenre() {
		return genre;
	}

	public void setGenre(GenreType genre) {
		this.genre = genre;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}
}
