package com.synacy.moviehouse.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @RequestMapping(method = RequestMethod.GET, value = "/{cinemaId}")
    public Cinema fetchCinema(@PathVariable(value="cinemaId") Long cinemaId) {
        return cinemaService.fetchCinemaById(cinemaId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Cinema> fetchAllCinemas(@RequestParam(value = "type", required = false) String type) {
        return (type == null) ? cinemaService.fetchAllCinemas() : cinemaService.fetchAllCinemasByType(type);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Cinema createNewCinema(@RequestBody Cinema cinema) {
        return  cinemaService.createCinema(cinema.getName(), cinema.getType());
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{cinemaId}")
    public Cinema updateCinema(@PathVariable(value="cinemaId") Long cinemaId,
                               @RequestBody Cinema cinemaRequest) {

        Cinema cinema = cinemaService.fetchCinemaById(cinemaId);
        return cinemaService.updateCinema(cinema, cinemaRequest.getName(), cinemaRequest.getType());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{cinemaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCinema(@PathVariable(value="cinemaId") Long cinemaId) {

        Cinema cinema = cinemaService.fetchCinemaById(cinemaId);
        cinemaService.deleteCinema(cinema);
    }

}
