package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by banjoe on 5/12/17.
 */

@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    CinemaRepo cinemaRepo;

    @Override
    public void setCinemaRepo(CinemaRepo cinemaRepo) {
        this.cinemaRepo = cinemaRepo;
    }

    @Override
    public Cinema fetchCinemaById(Long id) {
        return cinemaRepo.findOne(id);
    }

    @Override
    public Cinema saveNewCinema(String name, String type) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setType(type);
        return cinemaRepo.save(cinema);
    }

    @Override
    public List<Cinema> fetchAllCinema(String type) {
        if(type == null) {
            return (List<Cinema>) cinemaRepo.findAll();
        } else {
            return cinemaRepo.findAllByType(type);
        }
    }

    @Override
    public Cinema updateCinema(Cinema cinemaToBeUpdated, String name, String type) {
        cinemaToBeUpdated.setName(name);
        cinemaToBeUpdated.setType(type);
        return cinemaRepo.save(cinemaToBeUpdated);
    }

    @Override
    public void deleteCinema(Cinema cinema) {
        cinemaRepo.delete(cinema);
    }
}
