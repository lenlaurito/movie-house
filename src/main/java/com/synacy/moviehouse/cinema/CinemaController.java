package com.synacy.moviehouse.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/{cinemaId}")
    public ResponseEntity fetchCinema(@PathVariable(value="cinemaId") Long cinemaId) {
        Cinema cinema = cinemaService.fetchCinemaById(cinemaId);
        return ResponseEntity.ok().body(cinema);
    }

    @GetMapping
    public ResponseEntity fetchAllCinemas(@RequestParam(value = "type", required = false) String type) {
        List<Cinema> cinemas = cinemaService.fetchAllCinemas(type);
        return ResponseEntity.ok().body(cinemas);
    }

    @PostMapping
    public ResponseEntity createNewCinema(@RequestBody Cinema cinemaToCreate) {
        Cinema cinema = cinemaService.createCinema(cinemaToCreate.getName(), cinemaToCreate.getType());
        return ResponseEntity.status(HttpStatus.CREATED).body(cinema);
    }

    @PutMapping("/{cinemaId}")
    public ResponseEntity updateCinema(@PathVariable(value="cinemaId") Long cinemaId,
                                       @RequestBody Cinema cinemaRequest) {
        Cinema cinemaToBeUpdated = cinemaService.fetchCinemaById(cinemaId);
        Cinema cinema = cinemaService.updateCinema(cinemaToBeUpdated, cinemaRequest.getName(), cinemaRequest.getType());
        return ResponseEntity.ok().body(cinema);
    }

    @DeleteMapping("/{cinemaId}")
    public ResponseEntity deleteCinema(@PathVariable(value="cinemaId") Long cinemaId) {
        Cinema cinema = cinemaService.fetchCinemaById(cinemaId);
        cinemaService.deleteCinema(cinema);
        return ResponseEntity.noContent().build();
    }

}
