package com.fabien.movieapi.mapper;

import com.fabien.movieapi.dto.MovieDTO;
import com.fabien.movieapi.dto.MovieResponseDTO;
import com.fabien.movieapi.model.Movie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MovieMapper tests")
class MovieMapperTest {

    @Test
    void toEntity_shouldMapAllFieldsCorrectly(){
        MovieDTO dto = new MovieDTO("Inception","Nolan", 2010, "Sci-Fi", 8.0);

        Movie movie = MovieMapper.toEntity(dto);

        assertThat(movie.getTitle()).isEqualTo(dto.title());
        assertThat(movie.getDirector()).isEqualTo(dto.director());
        assertThat(movie.getGenre()).isEqualTo(dto.genre());
        assertThat(movie.getReleaseYear()).isEqualTo(dto.releaseYear());
        assertThat(movie.getRating()).isEqualTo(dto.rating());
    }

    @Test
    void toDto_shouldMapAllFieldsCorrectly() {
        Movie movie = new Movie(1L,"Inception","Nolan", 2010, "Sci-Fi", 8.0);

        MovieResponseDTO dto = MovieMapper.toDto(movie);

        assertThat(dto.getTitle()).isEqualTo(movie.getTitle());
        assertThat(dto.getDirector()).isEqualTo(movie.getDirector());
        assertThat(dto.getGenre()).isEqualTo(movie.getGenre());
        assertThat(dto.getReleaseYear()).isEqualTo(movie.getReleaseYear());
        assertThat(dto.getRating()).isEqualTo(movie.getRating());
    }
}