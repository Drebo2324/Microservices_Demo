package com.drebo.movie_ratings_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRating {

    private String movieId;
    private Rating rating;
}
