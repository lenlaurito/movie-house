package com.synacy.moviehouse.cinema;

import com.synacy.moviehouse.exceptions.MissingRequiredFieldsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kenichigouang on 5/12/17.
 */

@RestController
@RequestMapping(value = "api/v1/cinema")
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    @GetMapping
    public ResponseEntity<List<Cinema>> fetchAllCinemas(@RequestParam(value = "type", required = false) CinemaType cinemaType) {

        List<Cinema> cinemas =  cinemaService.fetchAll(cinemaType);
        return ResponseEntity.ok().body(cinemas);
    }

    @GetMapping("/{cinemaId}")
    public Cinema fetchCinema(@PathVariable(value = "cinemaId") Long cinemaId) {
        return cinemaService.fetchById(cinemaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cinema createCinema(@RequestBody Cinema cinemaRequest) {
        if(cinemaRequest.getName() == null || cinemaRequest.getType() == null){
            throw new MissingRequiredFieldsException("Some required data is missing.");
        } else {
            return cinemaService.createCinema(cinemaRequest.getName(), cinemaRequest.getType());
        }
    }

    @PutMapping("/{cinemaId}")
    public Cinema updateCinema(@PathVariable(value = "cinemaId") Long cinemaId,
                               @RequestBody Cinema cinemaRequest) {
        if(cinemaRequest.getName() == null || cinemaRequest.getType() == null){
            throw new MissingRequiredFieldsException("Some required data is missing.");
        } else {
            Cinema cinema = cinemaService.fetchById(cinemaId);
            return cinemaService.updateCinema(cinema, cinemaRequest.getName(), cinemaRequest.getType());
        }
    }

    @DeleteMapping("/{cinemaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCinema(@PathVariable(value = "cinemaId") Long cinemaId) {
        Cinema cinema = cinemaService.fetchById(cinemaId);
        cinemaService.deleteCinema(cinema);
    }
}
