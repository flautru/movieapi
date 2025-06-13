package com.fabien.movieapi.controller;


import com.fabien.movieapi.dto.MovieDTO;
import com.fabien.movieapi.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerErrorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/movie/9999 should return 404 when movie not found")
    void testGetMovieByIdNotFound() throws Exception {
        // Arrange
        long movieId = 9999L;
        when(movieService.getMovieById(movieId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/movie/" + movieId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("Movie not found with id " + movieId));
    }

    @Test
    @DisplayName("POST /api/movie/ with invalid payload should return 400")
    void testPostMovieWithInvalidData() throws Exception {
        // Arrange: title est vide, donc invalide si annoté @NotBlank
        MovieDTO invalidDto = new MovieDTO("", "Nolan", 2010, "Drama", 8.5);

        // Act & Assert
        mockMvc.perform(post("/api/movie/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    @DisplayName("Simuler une erreur serveur")
    void testInternalServerError() throws Exception {
        when(movieService.getMovieById(1L)).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/movie/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Unexpected error"));
    }
}