package com.drebo.movie_catalog_service.controller;

import com.drebo.movie_catalog_service.model.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping(path = "/{userId}")
    @CircuitBreaker(name = "catalogServiceCb", fallbackMethod = "getCatalogFb")
    //If using asynchronous method must return Mono instead of List
    public CatalogList getCatalog(@PathVariable("userId") String userId){

        UserRatingsList userRatingsList = restTemplate.getForObject("http://movie-ratings-service/ratings/users/" +userId, UserRatingsList.class);
        //For each movie ID in list -> call for movie info service
        assert userRatingsList != null;
        List<CatalogItem> catalogItemList = userRatingsList.getUserRatingsList().stream().map(movieRating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + movieRating.getMovieId(), Movie.class);
//            //WebClient ->Asynchronous
//            Movie movie = webClientBuilder.build()
//                    //crud method
//                    .get()
//                    //where
//                    .uri("http://localhost:8082/movies/" + movieRating.getMovieId())
//                    //fetch data
//                    .retrieve()
//                    //convert String(JSON) data into Object
//                    //Mono is a promise -> asynchronous
//                    .bodyToMono(Movie.class)
//                    //blocking execution until mono fulfilled -> remove block for async
//                    .block();
            assert movie != null;
            return new CatalogItem(movie.getTitle(), movie.getOverview(), movieRating.getRating());
        }).collect(Collectors.toList());

        return new CatalogList(catalogItemList);
    }

    public CatalogList getCatalogFb(String userId, Throwable t){
        System.err.println("Fallback due to: " + t.getMessage());
        List<CatalogItem> fallbackList = List.of(new CatalogItem("null", "null", Rating.AVERAGE));
        return new CatalogList(fallbackList);
    }
}
