package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.movie.Movie;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by banjoe on 5/12/17.
 */
public interface CinemaService {
    Cinema fetchCinemaById(Long id);
    Cinema saveNewCinema(String name, String type);
    List<Cinema> fetchAllCinema(String type);
    Cinema updateCinema(Cinema cinemaToBeUpdated, String name, String type);
    void deleteCinema(Cinema cinema);
    void setCinemaRepo(CinemaRepo cinemaRepo);
}
