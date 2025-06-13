package com.fabien.movieapi.controller;

import com.fabien.movieapi.dto.MovieDTO;
import com.fabien.movieapi.dto.MovieResponseDTO;
import com.fabien.movieapi.model.Movie;
import com.fabien.movieapi.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/movie/ - should return paginated movies")
    void shouldReturnPaginatedMovies() throws Exception {
        Movie movie = new Movie(1L, "Title", "Director", 2020, "Action", 8.0);
        var page = new PageImpl<>(List.of(movie), PageRequest.of(0, 10), 1);

        Mockito.when(movieService.getAllMovie(0, 10, "id", "asc")).thenReturn(page);

        mockMvc.perform(get("/api/movie/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Title"));
    }

    @Test
    @DisplayName("GET /api/movie/{id} - should return a movie")
    void shouldReturnMovieById() throws Exception {
        Movie movie = new Movie(1L, "Title", "Director", 2020, "Action", 8.0);
        Mockito.when(movieService.getMovieById(1L)).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/api/movie/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    @DisplayName("POST /api/movie/ - should create a movie")
    void shouldCreateMovie() throws Exception {
        MovieDTO dto = new MovieDTO("Title", "Director", 2020, "Action", 8.0);
        MovieResponseDTO response = new MovieResponseDTO(1L, "Title", "Director", 2020, "Action", 8.0);

        Mockito.when(movieService.createMovie(any(MovieDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/movie/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("PUT /api/movie/ - should update a movie")
    void shouldUpdateMovie() throws Exception {
        MovieDTO update = new MovieDTO("Updated", "New Director", 2021, "Drama", 9.0);
        MovieResponseDTO updated = new MovieResponseDTO(1L, "Updated", "New Director", 2021, "Drama", 9.0);

        Mockito.when(movieService.updateMovie(Mockito.eq(1L), any(MovieDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/movie/")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    @DisplayName("DELETE /api/movie/{id} - should delete movie by id")
    void shouldDeleteMovieById() throws Exception {
        mockMvc.perform(delete("/api/movie/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Movie successfully deleted with id 1"));
    }

    @Test
    @DisplayName("DELETE /api/movie/ - should delete movie by entity")
    void shouldDeleteMovieByEntity() throws Exception {
        Movie movie = new Movie(1L, "Title", "Director", 2020, "Action", 8.0);

        mockMvc.perform(delete("/api/movie/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andExpect(content().string("Movie successfully deleted"));
    }
}
