package com.fabien.movieapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {

    private Long id;
    private String title;
    private String director;
    private int releaseYear;
    private String genre;
    private double rating;

}
