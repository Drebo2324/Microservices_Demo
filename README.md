# Overview
This project consists of the microservices: Movie Catalog Service, Movie Ratings Service, Movie Info Service, and the Eureka Server. This is a simple movie catalog system that will allow users to create a list of movies and attach ratings to them. 

I used Eureka for service discovery between the microservices. The Movie Catalog Service fetches data from both the Movie Info Service and the Movie Ratings Service to consolidate the data and return it to the client. I implemented fault-tolerant communication between services using Resilience4J to handle potential failures. 

The Movie Catalog Service first takes a “UserId” from the client and makes a call to the Movie Ratings Service, which returns “MovieId” and “Ratings”. The Movie Catalog Service then makes a call to the Movie Info Service using the acquired “MovieId” and gets back “Movie Details”. Once the Movie Catalog Service has both the “Ratings” and “Movie details”, it returns a list of movies associated with the “UserId” containing “Ratings” and “Movie Details”. 

# Tech Stack:
- Spring MVC
- Spring Cloud
- Resilience4J
- Eureka

# Movie Catalog Service 
- Input: UserId 
- Output: List of movies with ratings and details 
- Makes calls to Movie Ratings Service and Movie Info  
- Circuit breaker using Resilience4J to handle failures when making service calls 

# Movie Ratings Service 
- Input: UserId 
- Output: MovieId and Ratings 

# Movie Info Service 
- Input: MovieId 
- Output: Movie details 
= Fetch movie information from “themoviedb.com” using Rest Template 

# Eureka Server 
- Microservices register (publish) using Eureka Client 
- Microservices locate (consume) using Eureka Client 
