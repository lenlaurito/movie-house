package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.InvalidRequestException;
import com.synacy.moviehouse.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    public Cinema fetchById(Long id) {
        Cinema cinema = cinemaRepository.findOne(id);
        if (cinema == null) {
            throw new ResourceNotFoundException("Cinema " + id + " does not exist.");
        }
        return cinema;
    }

    public List<Cinema> fetchAll() {
        return (List) cinemaRepository.findAll();
    }

    public List<Cinema> fetchAllByType(String type) {
        if (!CinemaType.contains(type.toUpperCase())) {
            throw new InvalidRequestException("Cinema Type " + type + " not found.");
        }
        return cinemaRepository.findAllByType(CinemaType.valueOf(type));
    }

    public Cinema create(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public Cinema update(Cinema cinema, String name, CinemaType type) {
        cinema.setName(name);
        cinema.setType(type);
        return cinemaRepository.save(cinema);
    }

    public void delete(Cinema cinema) {
        cinemaRepository.delete(cinema);
    }

}
