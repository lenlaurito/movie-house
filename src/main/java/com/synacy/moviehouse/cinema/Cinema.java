package com.synacy.moviehouse.cinema;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
public class Cinema {

	@Id @GeneratedValue
	private Long id;

	@Setter @NotNull
	private String name;

	@Setter @NotNull
	@Enumerated(EnumType.STRING)
	private CinemaType type;

}
