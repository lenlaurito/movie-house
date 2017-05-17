package com.synacy.moviehouse.cinema;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Cinema {

	@Id @GeneratedValue @Setter @Getter
	private Long id;

	@NotNull @Setter @Getter
	private String name;

	@NotNull @Setter @Getter
	private String type;

}
