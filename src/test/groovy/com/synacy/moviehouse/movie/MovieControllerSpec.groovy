package com.synacy.moviehouse.movie

import spock.lang.Specification

class MovieControllerSpec extends Specification {

    MovieController movieController
    MovieService movieService = Mock(movieService)

    void setup() {
        movieController = new MovieController()
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
}
