package com.synacy.moviehouse.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenre(String genre);
    List<Movie> findByName(String name);
    List<Movie> findByGenreAndName(String genre, String name);
}
