package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.NoContentFoundException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by steven on 5/12/17.
 */

@Service
@Transactional
public class CinemaService {

    @Autowired @Setter
    CinemaRepository cinemaRepository;

    public Cinema fetchById(Long id) {

        Cinema cinema = cinemaRepository.findOne(id);

        if (cinema == null)
            throw new NoContentFoundException("Not content found");
        else
            return cinema;
    }

    public List<Cinema> fetchAll() {

        List<Cinema> cinemas = (List) cinemaRepository.findAll();

        if(cinemas.size() < 1)
            throw new NoContentFoundException("Not content found");
        else
            return cinemas;
    }

    public List<Cinema> fetchAllByType(CinemaType cinemaType) {

        List<Cinema> cinemas = (List) cinemaRepository.findAllByType(cinemaType);

        if(cinemas.size() < 1)
            throw new NoContentFoundException("Not content found");
        else
            return cinemas;
    }

    public Cinema createCinema(String name, CinemaType type) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setType(type);
        return cinemaRepository.save(cinema);
    }

    public Cinema updateCinema(Cinema cinema, String name, CinemaType type){
        cinema.setName(name);
        cinema.setType(type);
        return cinemaRepository.save(cinema);
    }

    public void deleteCinema(Cinema cinema){
        cinemaRepository.delete(cinema);
    }


}
