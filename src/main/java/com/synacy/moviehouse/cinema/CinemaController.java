package com.synacy.moviehouse.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by michael on 5/15/17.
 */
@RestController
@RequestMapping(value = "/api/v1/cinema")
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Cinema> fetchAllCinema(Pageable pageable,
                                       @RequestParam(value = "type", required = false) String type) {

        Sort sort = new Sort(Sort.Direction.ASC, "type");
        PageRequest pageRequest = new PageRequest(pageable.getOffset(), pageable.getPageSize(), sort);

        return cinemaService.fetchAllCinema(pageRequest, type);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Cinema createCinema(@RequestBody Cinema cinemaRequest) {

        return cinemaService.createCinema(cinemaRequest.getName(), cinemaRequest.getType());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cinemaId}")
    public Cinema fetchCinemaById(@PathVariable(value = "cinemaId")Long cinemaId) {
        return cinemaService.fetchCinemaById(cinemaId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{cinemaId}")
    public Cinema updateCinema(@PathVariable(value = "cinemaId")Long cinemaId,
                               @RequestBody Cinema cinemaRequest) {

        return cinemaService.updateCinema(cinemaId, cinemaRequest.getName(), cinemaRequest.getType());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{cinemaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCinema(@PathVariable(value = "cinemaId") Long cinemaId) {
        cinemaService.deleteCinemaById(cinemaId);
    }
}
