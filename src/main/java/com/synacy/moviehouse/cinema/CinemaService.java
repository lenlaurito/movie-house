package com.synacy.moviehouse.cinema;

import java.util.List;

public interface CinemaService {

    Cinema fetchCinemaById(Long id);

    List<Cinema> fetchAllCinemas(String type);

    Cinema createCinema(String name, CinemaType type);

    Cinema updateCinema(Cinema cinema, String name, CinemaType type);

    void deleteCinema(Cinema cinema);

}
