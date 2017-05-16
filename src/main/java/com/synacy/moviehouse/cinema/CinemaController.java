package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.MissingParameterException;
import com.synacy.moviehouse.ResourceNotFoundException;
import com.synacy.moviehouse.movie.Movie;
import com.synacy.moviehouse.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by banjoe on 5/12/17.
 */

@RestController
@RequestMapping(value = "/api/v1/cinema")
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    public void setCinemaService(CinemaService cinemaService){
        this.cinemaService = cinemaService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cinemaId}")
    public Cinema fetchCinemaById(@PathVariable Long cinemaId){

        Cinema cinema = cinemaService.fetchCinemaById(cinemaId);

        if(cinema == null) {
            throw new ResourceNotFoundException("Cinema does not exist");
        }

        return cinema;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity fetchAllCinema(@RequestParam(value = "type", required = false) String type) {
        List<Cinema> allCinemas = cinemaService.fetchAllCinema(type);

        if(allCinemas.isEmpty()){
            throw new ResourceNotFoundException("No existing Cinema found.");
        }

        return ResponseEntity.ok().body(allCinemas);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Cinema createCinema(@RequestBody Cinema cinema) {
        return cinemaService.saveNewCinema(cinema.getName(), cinema.getType());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{cinemaId}")
    public Cinema updateCinema(@PathVariable Long cinemaId, @RequestBody Cinema cinemaRequest) {
        Cinema cinema = cinemaService.fetchCinemaById(cinemaId);

        if(cinema == null) {
            throw new ResourceNotFoundException("Cinema does not exist");
        }

        return cinemaService.updateCinema(cinema, cinemaRequest.getName(), cinemaRequest.getType());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{cinemaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCinema(@PathVariable(value="cinemaId") Long cinemaId) {
        Cinema cinema = cinemaService.fetchCinemaById(cinemaId);

        if (cinema == null) {
            throw new ResourceNotFoundException("Cinema does not exist");
        }

        cinemaService.deleteCinema(cinema);
    }


}
