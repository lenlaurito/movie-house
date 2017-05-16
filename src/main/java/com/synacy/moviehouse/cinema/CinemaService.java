package com.synacy.moviehouse.cinema;

import java.util.List;

public interface CinemaService {

    Cinema fetchById(Long id);

    List<Cinema> fetchAll();

    List<Cinema> fetchAllByType(String type);

    Cinema create(Cinema cinema);

    Cinema update(Cinema cinema, String name, CinemaType type);

    void delete(Cinema cinema);

}
