package com.synacy.moviehouse.movie

import com.synacy.moviehouse.exception.MovieAlreadyExists
import com.synacy.moviehouse.exception.MovieAlreadyExistsException
import spock.lang.Specification

class MovieServiceSpec extends Specification {

    MovieService movieService

    MovieRepository movieRepository = Mock(MovieRepository)

    void setup() {
        movieService = new MovieService(movieRepository)
    }

    void cleanup() {}

    def "createNewMovie should return a Movie object"() {
        given:
        Movie expectedMovie = Mock(Movie)

        movieRepository.save(_ as Movie) >> expectedMovie

        when:
        Movie actualMovie = movieService.createNewMovie(expectedMovie)

        then:
        expectedMovie == actualMovie
    }

    def "createNewMovie should throw MovieAlreadyExistsException if given movie already exists"() {
        given:
        Movie mockMovie = Mock(Movie)

        movieRepository.findOne(mockMovie) >> Optional.of(mockMovie)

        when:
        movieService.createNewMovie(mockMovie)

        then:
        thrown(MovieAlreadyExistsException)
    }
}
