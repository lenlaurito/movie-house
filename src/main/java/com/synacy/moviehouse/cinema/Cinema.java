package com.synacy.moviehouse.cinema;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Cinema {

	@Id @GeneratedValue
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String type;

}
