package com.synacy.moviehouse.movie;

import com.synacy.moviehouse.exception.InvalidRequestException;
import com.synacy.moviehouse.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Movie fetchById(Long id) {
        Movie movie = movieRepository.findOne(id);
        if (movie == null) {
            throw new ResourceNotFoundException("Movie with id " + id + " does not exist.");
        }
        return movie;
    }

    public List<Movie> fetchAll() {
        return (List) movieRepository.findAll();
    }

    public List<Movie> fetchAll(String name, String genre, Integer offset, Integer max) {
        if (offset != null && max != null) {
            return this.fetchAllWithPagination(name, genre, offset, max).getContent();
        }
        else {
            if (name != null && genre != null) {
                if (offset == null && max == null) {
                    return this.fetchAllByNameAndGenre(name, genre);
                } else {
                    throw new InvalidRequestException("Offset and max should both be used as parameters.");
                }
            }
            else {
                if (offset == null && max == null) {
                    return this.fetchAllByNameAndGenre(name, genre);
                }
                else {
                    throw new InvalidRequestException("Offset and max should both be used as parameters.");
                }
            }
        }
    }

    public Movie create(Movie movie) {
        movie.setName(movie.getName());
        movie.setGenre(movie.getGenre());
        movie.setDuration(movie.getDuration());
        movie.setDescription(movie.getDescription());
        return movieRepository.save(movie);
    }

    public Movie update(Movie movie, String name, Genre genre, Integer duration, String description) {
        movie.setName(name);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movie.setDescription(description);
        return movieRepository.save(movie);
    }

    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    public List<Movie> fetchAllByNameAndGenre(String name, String genre) {
        if (genre == null) {
            return movieRepository.findMoviesByNameContainingIgnoreCase(name);
        }
        else if (name == null) {
            return movieRepository.findAllByGenre(Genre.valueOf(genre.toUpperCase()));
        }
        else {
            return movieRepository.findAllByNameContainingIgnoreCaseAndGenre(name, Genre.valueOf(genre.toUpperCase()));
        }
    }

    protected Page<Movie> fetchAllWithPagination(String name, String genre, Integer offset, Integer max) {
        Pageable pageable = new PageRequest(offset, max, Sort.Direction.ASC, "name");
        if (name == null && genre == null) {
            return movieRepository.findAll(pageable);
        } else if (name == null) {
            return movieRepository.findAllByGenre(Genre.valueOf(genre.toUpperCase()), pageable);
        } else if (genre == null) {
            return movieRepository.findAllByNameContainingIgnoreCase(name, pageable);
        } else {
            return movieRepository.findAllByNameContainingIgnoreCaseAndGenre(name, Genre.valueOf(genre.toUpperCase()), pageable);
        }
    }

    public List<Movie> fetchAllByGenre(String genre) {
        return movieRepository.findAllByGenre(Genre.valueOf(genre.toUpperCase()));
    }

}
