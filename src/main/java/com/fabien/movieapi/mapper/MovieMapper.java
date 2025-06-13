package com.fabien.movieapi.mapper;

import com.fabien.movieapi.dto.MovieDTO;
import com.fabien.movieapi.dto.MovieResponseDTO;
import com.fabien.movieapi.model.Movie;

public class MovieMapper{

    public static Movie toEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.title());
        movie.setDirector(dto.director());
        movie.setReleaseYear(dto.releaseYear());
        movie.setGenre(dto.genre());
        movie.setRating(dto.rating());
        return movie;
    }

    public static MovieResponseDTO toDto(Movie movie) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setGenre(movie.getGenre());
        dto.setRating(movie.getRating());
        return dto;
    }
}
