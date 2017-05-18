package com.synacy.moviehouse.cinema;

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
public class Cinema {

	@Id @GeneratedValue @Setter
	private Long id;

	@Setter
	@NotNull
	private String name;

	@Setter @NotNull
	private String type;
}
