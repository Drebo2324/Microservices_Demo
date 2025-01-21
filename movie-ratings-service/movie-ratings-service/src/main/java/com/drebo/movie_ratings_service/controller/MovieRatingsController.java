package com.drebo.movie_ratings_service.controller;

import com.drebo.movie_ratings_service.model.MovieRating;
import com.drebo.movie_ratings_service.model.Rating;
import com.drebo.movie_ratings_service.model.UserRatingsList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/ratings")
public class MovieRatingsController {

    @RequestMapping(path = "{movieId}")
    public MovieRating getRating(@PathVariable("movieId") String movieId){
        return new MovieRating(movieId, Rating.GREAT);
    }

    @RequestMapping(path = "/users/{userId}")
    public UserRatingsList getUserRatings(@PathVariable("userId") String userId){

        UserRatingsList userRatingsList = new UserRatingsList();
        userRatingsList.initData(userId);
        return userRatingsList;
    }
}
