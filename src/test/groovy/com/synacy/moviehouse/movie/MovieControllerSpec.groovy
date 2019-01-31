package com.synacy.moviehouse.movie

import spock.lang.Specification

class MovieControllerSpec extends Specification {

    MovieController movieController
    MovieService movieService = Mock(MovieService)

    void setup() {
        movieController = new MovieController(movieService)
    }

    void cleanup() {}

    def "createNewMovie should return created Movie Object"() {
        given:
        Movie expectedMovie = Mock(Movie)


        movieService.createNewMovie(expectedMovie) >> expectedMovie

        when:
        Movie actualMovie = movieController.createNewMovie(expectedMovie)

        then:
        expectedMovie == actualMovie
    }

    def "updateMovie should return movie with updated content"() {
        given:
        Movie expectedMovie = Mock(Movie)

        expectedMovie.getName() >> "Avengers"
        expectedMovie.getGenre() >> "Action"
        expectedMovie.getId() >> 1
        expectedMovie.getDuration() >> 150
        expectedMovie.getDescription() >> "Updated Description"

        when:
        movieController.updateMovie(expectedMovie, 1)

        then:
        1 == expectedMovie.id
        "Avengers" == expectedMovie.name
        "Action" == expectedMovie.genre
        150 == expectedMovie.duration
        "Updated Description" == expectedMovie.description
    }

}
