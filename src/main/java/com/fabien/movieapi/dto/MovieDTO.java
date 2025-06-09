package com.fabien.movieapi.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String director;

    @Min(1900)
    @Max(2100)
    private int releaseYear;

    @NotBlank
    private String genre;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private double rating;
}

