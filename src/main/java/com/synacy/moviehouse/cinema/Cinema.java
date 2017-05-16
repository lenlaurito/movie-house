package com.synacy.moviehouse.cinema;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(of = {"id"})
@Entity
@JsonSerialize(using = CinemaSerializer.class)
public class Cinema {

	@Id @NotNull @GeneratedValue
	private Long id;

	@NotNull
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CinemaType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CinemaType getType() {
		return type;
	}

	public void setType(CinemaType type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}
}
