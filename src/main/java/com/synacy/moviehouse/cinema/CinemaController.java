package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exception.IncompleteInformationException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by steven on 5/12/17.
 */

@RestController
@RequestMapping(value = "/api/v1/cinema")
public class CinemaController {

    @Autowired @Setter
    CinemaService cinemaService;

    @RequestMapping(method = RequestMethod.GET, value = "/{cinemaId}")
    public Cinema fetchCinema(@PathVariable(value="cinemaId") Long cinemaId) {
        return cinemaService.fetchById(cinemaId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Cinema> fetchAllCinema(@RequestParam(value = "type", required = false) CinemaType cinemaType){
        if(cinemaType == null)
            return  cinemaService.fetchAll();
        else
            return  cinemaService.fetchAllByType(cinemaType);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Cinema createCinema(@RequestBody Cinema cinema){
        if(cinema.getName() == null || cinema.getType() == null)
            throw new IncompleteInformationException("Missing some required information");

        return cinemaService.createCinema(cinema.getName(),cinema.getType());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{cinemaId}")
    public Cinema updateCinema(@PathVariable(value = "cinemaId") Long cinemaId, @RequestBody Cinema cinema){
        if(cinema.getName() == null || cinema.getType() == null)
            throw new IncompleteInformationException("Missing some required information");

        return cinemaService.updateCinema(cinemaId, cinema.getName(), cinema.getType());

    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/{cinemaId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCinema(@PathVariable(value = "cinemaId") Long cinemaId){
        cinemaService.deleteCinema(cinemaId);
    }

}
