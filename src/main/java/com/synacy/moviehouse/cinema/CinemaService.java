package com.synacy.moviehouse.cinema;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by michael on 5/15/17.
 */
@Service
@Transactional
public class CinemaService {

    @Autowired @Setter @Getter
    CinemaRepository cinemaRepository;

    public List<Cinema> fetchAllCinema(Pageable pageable, String type) {
        Page<Cinema> cinemaPage;

        if (type == null)
            cinemaPage = cinemaRepository.findAll(pageable);
        else
            cinemaPage = cinemaRepository.findAllCinemaByTypeContaining(type, pageable);

        return cinemaPage.getContent();
    }

    public Cinema createCinema(String name, String type) {
        Cinema cinema = new Cinema();

        cinema.setName(name);
        cinema.setType(type);

        return cinemaRepository.save(cinema);
    }

    public Cinema fetchCinemaById(Long cinemaId) {
        Cinema cinema = cinemaRepository.findOne(cinemaId);

        if (cinema == null)
            throw new NoResultException();

        return cinema;
    }

    public Cinema updateCinema(Long cinemaId, String name, String type) {
        Cinema cinema = fetchCinemaById(cinemaId);

        cinema.setName(name);
        cinema.setType(type);

        return cinemaRepository.save(cinema);
    }

    public void deleteCinemaById(Long cinemaId) {
        cinemaRepository.delete(fetchCinemaById(cinemaId));
    }
}
