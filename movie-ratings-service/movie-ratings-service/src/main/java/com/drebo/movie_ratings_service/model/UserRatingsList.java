package com.drebo.movie_ratings_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRatingsList {

    private String userId;
    private List<MovieRating> userRatingsList;

    public void initData(String userId){
        this.setUserId(userId);
        this.setUserRatingsList(Arrays.asList(
                new MovieRating("500", Rating.AVERAGE),
                new MovieRating("200", Rating.AVERAGE)
        ));

    }
}
