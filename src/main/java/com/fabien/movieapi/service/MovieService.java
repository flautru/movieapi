package com.fabien.movieapi.service;

import com.fabien.movieapi.dto.MovieDTO;
import com.fabien.movieapi.dto.MovieResponseDTO;
import com.fabien.movieapi.exception.ResourceNotFoundException;
import com.fabien.movieapi.mapper.MovieMapper;
import com.fabien.movieapi.model.Movie;
import com.fabien.movieapi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Page<Movie> getAllMovie(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return movieRepository.findAll(pageable);
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public MovieResponseDTO createMovie(MovieDTO movieDto){
        Movie movie = MovieMapper.toEntity(movieDto);
        Movie savedMovie = movieRepository.save(movie);
        return MovieMapper.toDto(savedMovie);
    }

    public MovieResponseDTO updateMovie(Long id,MovieDTO updateMovie){
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + id));

        existingMovie.setDirector(updateMovie.director());
        existingMovie.setGenre(updateMovie.genre());
        existingMovie.setTitle(updateMovie.title());
        existingMovie.setReleaseYear(updateMovie.releaseYear());
        existingMovie.setRating(updateMovie.rating());

        existingMovie = movieRepository.save(existingMovie);
        return MovieMapper.toDto(existingMovie);
    }

    public void deleteMovieById(Long id){
        movieRepository.deleteById(id);
    }

    public void deleteMovie(Movie deletMovie){
        movieRepository.delete(deletMovie);
    }
}
