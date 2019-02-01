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

    def "getMovies should return list of all movies if there are no parameters"() {
        given:
        List <Movie> expectedMovies = buildMovies()

        movieService.getAllMovies() >> expectedMovies

        when:
        List <Movie> actualMovies = movieController.getMovies(null, null)

        then:
        expectedMovies == actualMovies
    }

    def "getMovies should return list of movies based on the parameter genre"() {
        given:
        List<Movie> movies = buildMovies()
        movies[0].genre >> "Action"
        movies[1].genre >> "Tragedy"
        movies[2].genre >> "Action"

        movieService.getMoviesByGenre("Action") >> [movies[0], movies[2]]

        when:
        List <Movie> moviesByGenre = movieController.getMovies("Action", null)

        then:
        moviesByGenre.each { Movie movie ->
            assert "Action" == movie.genre
        }
    }

    def "getMovies should return list of movies based on the parameter name"() {
        given:
        List<Movie> movies = buildMovies()
        movies[0].name >> "Same"
        movies[1].name >> "Same"
        movies[2].name >> "Different"

        movieService.getMoviesByName("Same") >> [movies[0], movies[1]]

        when:
        List <Movie> moviesByName = movieController.getMovies(null, "Same")

        then:
        moviesByName.each { Movie movie ->
            assert "Same" == movie.name
        }
    }

    def "getMovies should return list of movies based on the parameters genre and name"() {
        given:
        List<Movie> expectedMovies = buildMovies()
        expectedMovies[0].name >> "Avengers"
        expectedMovies[1].name >> "Avengers"
        expectedMovies[2].name >> "Die Hard"
        expectedMovies[0].genre >> "Action"
        expectedMovies[1].genre >> "Action"
        expectedMovies[2].genre >> "Action"

        movieService.getMoviesByGenreAndName("Action", "Avengers") >> [expectedMovies[0], expectedMovies[1]]

        when:
        List<Movie> moviesByGenreAndName = movieController.getMovies("Action", "Avengers")

        then:
        moviesByGenreAndName.each { Movie movie ->
            assert "Action" == movie.genre
            assert "Avengers" == movie.name
        }
    }

    List <Movie> buildMovies() {
        Movie firstMovie = Mock(Movie)
        Movie secondMovie = Mock(Movie)
        Movie thirdMovie = Mock(Movie)

        return [firstMovie, secondMovie, thirdMovie]
    }

}
