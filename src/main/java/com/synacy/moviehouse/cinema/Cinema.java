package com.synacy.moviehouse.cinema;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
@JsonSerialize(using = CinemaSerializer.class)
public class Cinema {

	@Id @GeneratedValue
	private Long id;

	@NotNull @Setter
	private String name;

	@NotNull @Setter
	@Enumerated(EnumType.STRING)
	private CinemaType type;

}
