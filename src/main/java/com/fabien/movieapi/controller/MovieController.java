package com.fabien.movieapi.controller;

import com.fabien.movieapi.dto.MovieDTO;
import com.fabien.movieapi.dto.MovieResponseDTO;
import com.fabien.movieapi.exception.ResourceNotFoundException;
import com.fabien.movieapi.model.Movie;
import com.fabien.movieapi.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/movie")
@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/")
    ///movies?page=1&size=5&sortBy=releaseYear&direction=desc
    public Page<Movie> getAllMovie (@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy,
                                    @RequestParam(defaultValue = "asc") String direction
    ) {
        return movieService.getAllMovie(page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id){
        Optional<Movie> existingMovie = movieService.getMovieById(id);
        return existingMovie.orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + id));
    }

    @PostMapping("/")
    public ResponseEntity<MovieResponseDTO> postMovie (@RequestBody @Valid MovieDTO saveMovie){
        MovieResponseDTO createMovie = movieService.createMovie(saveMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createMovie);
    }

    @PutMapping("/")
    public ResponseEntity<MovieResponseDTO> putMovie(@RequestParam Long id,@RequestBody @Valid MovieDTO updateMovie){
        MovieResponseDTO updatedMovie = movieService.updateMovie(id, updateMovie);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovieById(@PathVariable Long id){
        movieService.deleteMovieById(id);
        return ResponseEntity.ok("Movie successfully deleted with id " + id);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteMovie(@RequestBody Movie deletedMovie){
        movieService.deleteMovie(deletedMovie);
        return ResponseEntity.ok("Movie successfully deleted");
    }
}
