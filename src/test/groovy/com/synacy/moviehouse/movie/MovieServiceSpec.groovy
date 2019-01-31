package com.synacy.moviehouse.movie

import spock.lang.Specification

class MovieServiceSpec extends Specification {

    MovieService movieService

    MovieRepository movieRepository = Mock(MovieRepository)

    void setup() {
        movieService = new MovieService(movieRepository)
    }

    void cleanup() {}

    def "CreateNewMovie should return a Movie object"() {
        given:
        Movie expectedMovie = Mock(Movie)

        movieRepository.save(_ as Movie) >> expectedMovie

        when:
        Movie actualMovie = movieService.createNewMovie(expectedMovie)

        then:
        expectedMovie == actualMovie
    }
}
