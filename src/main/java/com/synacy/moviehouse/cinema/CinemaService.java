package com.synacy.moviehouse.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    public Cinema fetchById(Long id) {
        return cinemaRepository.findOne(id);
    }

    public List<Cinema> fetchAll() {
        return (List) cinemaRepository.findAll();
    }

    public Cinema createCinema(String name, String type) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setType(type);
        return cinemaRepository.save(cinema);
    }

    public Cinema updateCinema(Cinema cinemaToBeUpdated, String name, String type) {
        cinemaToBeUpdated.setName(name);
        cinemaToBeUpdated.setType(type);
        return cinemaRepository.save(cinemaToBeUpdated);
    }

    public void deleteCinema(Cinema cinemaToBeDeleted) {
        cinemaRepository.delete(cinemaToBeDeleted);
    }
}
