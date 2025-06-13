package com.fabien.movieapi.dto;


import jakarta.validation.constraints.*;

public record MovieDTO(
        @NotBlank String title,
        @NotBlank String director,
        @Min(1900) @Max(2100) int releaseYear,
        @NotBlank String genre,
        @DecimalMin("0.0") @DecimalMax("10.0") double rating
) {}

