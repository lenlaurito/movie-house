package com.synacy.moviehouse.cinema;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return null;
    }

    public Cinema saveCinema(Cinema cinema) {
        return null;
    }

    public Cinema fetchCinemaById(Long cinemaId) {
        return null;
    }

    public void deleteCinemaById(Long cinemaId) {

    }
}
