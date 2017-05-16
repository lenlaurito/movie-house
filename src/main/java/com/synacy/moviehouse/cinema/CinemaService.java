package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exceptions.NoContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */

@Service
@Transactional
public class CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    public List<Cinema> fetchAll() {
        List<Cinema> cinemas = (List) cinemaRepository.findAll();

        if(cinemas.isEmpty()) {
            throw new NoContentException("No cinemas exist.");
        } else {
            return cinemas;
        }
    }

    public Cinema fetchById(Long id) {
        Cinema cinema = cinemaRepository.findOne(id);

        if(cinema == null) {
            throw new NoContentException("Cinema does not exist.");
        }

        return cinema;
    }

    public Cinema createCinema(String name, CinemaType type) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setType(type);
        return cinemaRepository.save(cinema);
    }

    public Cinema updateCinema(Cinema cinemaToBeUpdated, String name, CinemaType type) {
        cinemaToBeUpdated.setName(name);
        cinemaToBeUpdated.setType(type);
        return cinemaRepository.save(cinemaToBeUpdated);
    }

    public void deleteCinema(Cinema cinemaToBeDeleted) {
        cinemaRepository.delete(cinemaToBeDeleted);
    }

    public List<Cinema> fetchAllByType(CinemaType cinemaType) {

        List<Cinema> cinemas = (List) cinemaRepository.findAllByType(cinemaType);

        if(cinemas.isEmpty())
            throw new NoContentException("There are no cinemas of that type.");
        else
            return cinemas;
    }

    public void setCinemaRepository(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }
}
