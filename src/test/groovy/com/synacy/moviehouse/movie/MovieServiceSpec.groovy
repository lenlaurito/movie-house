package com.synacy.moviehouse.movie

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

        expectedMovie.getId() >> 1

        movieRepository.findById(expectedMovie.getId()) >> Optional.empty()
        movieRepository.save(_ as Movie) >> expectedMovie

        when:
        Movie actualMovie = movieService.createNewMovie(expectedMovie)

        then:
        expectedMovie == actualMovie
    }

    def "createNewMovie should throw MovieAlreadyExistsException if given movie already exists"() {
        given:
        Movie mockMovie = Mock(Movie)

        mockMovie.getId() >> 1

        movieRepository.findById(mockMovie.getId()) >> Optional.of(mockMovie)

        when:
        movieService.createNewMovie(mockMovie)

        then:
        thrown(MovieAlreadyExistsException)
    }

    def "updateMovie should update content of movie"() {
        given:
        Movie expectedMovie = Mock(Movie)

        expectedMovie.getName() >> "Avengers"
        expectedMovie.getGenre() >> "Action"
        expectedMovie.getId() >> 1
        expectedMovie.getDuration() >> 150
        expectedMovie.getDescription() >> "Updated Description"

        when:
        movieService.updateMovie(expectedMovie)

        then:
        1 == expectedMovie.id
        "Avengers" == expectedMovie.name
        "Action" == expectedMovie.genre
        150 == expectedMovie.duration
        "Updated Description" == expectedMovie.description
    }
}
