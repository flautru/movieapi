package com.fabien.movieapi.service;

import com.fabien.movieapi.dto.MovieDTO;
import com.fabien.movieapi.dto.MovieResponseDTO;
import com.fabien.movieapi.exception.ResourceNotFoundException;
import com.fabien.movieapi.mapper.MovieMapper;
import com.fabien.movieapi.model.Movie;
import com.fabien.movieapi.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRetunAllMoviesPageAndSorted() {
        Movie movie = new Movie(1L, "Inception", "Nolan", 2010, "Sci-Fi",9.0);
        Page<Movie> page = new PageImpl<>(List.of(movie));
        when(movieRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Movie> result = movieService.getAllMovie(0,10,"title","asc");

        assertEquals(1,result.getTotalElements());
        verify(movieRepository).findAll(any(Pageable.class));
    }

    @Test
    void shouldReturnMovieById() {
        Movie movie = new Movie(1L, "Inception", "Nolan", 2010, "Sci-Fi",9.0);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.getMovieById(1L);

        assertTrue(result.isPresent());
        assertEquals(movie,result.get());
    }

    @Test
    void shouldCreateMovie() {
        MovieDTO dto = new MovieDTO("Inception", "Nolan", 2010, "Sci-Fi",9.0);
        Movie entity = MovieMapper.toEntity(dto);
        entity.setId(1L);
        when(movieRepository.save(any(Movie.class))).thenReturn(entity);

        MovieResponseDTO response = movieService.createMovie(dto);

        assertNotNull(response);
        assertEquals("Inception", response.getTitle());
    }

    @Test
    void updateMovie() {
        Movie existing =  new Movie(1L, "old", "old", 2010, "old-Fi",5.0);
        MovieDTO updated = new MovieDTO("new", "new", 2020, "new-Fi",9.0);
        Movie entity = MovieMapper.toEntity(updated);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(movieRepository.save(any(Movie.class))).thenReturn(entity);

        MovieResponseDTO response = movieService.updateMovie(1L, updated);

        assertEquals(updated.title(),response.getTitle());
        assertEquals(updated.director(),response.getDirector());
        assertEquals(updated.releaseYear(),response.getReleaseYear());
        assertEquals(updated.genre(),response.getGenre());
        assertEquals(updated.rating(),response.getRating());
    }

    @Test
    void shouldThrowIfMovieNotFoundUpdate(){
        MovieDTO updated = new MovieDTO("new", "new", 2020, "new-Fi",9.0);

        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.updateMovie(1L, updated));
    }
    @Test
    void deleteMovieById() {

        movieService.deleteMovieById(1L);

        verify(movieRepository).deleteById(1L);
    }

    @Test
    void deleteMovie() {
        Movie movie = new Movie(1L, "Inception", "Nolan", 2010, "Sci-Fi", 9.0);

        movieService.deleteMovie(movie);

        verify(movieRepository).delete(movie);
    }
}