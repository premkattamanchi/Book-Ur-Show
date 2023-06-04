package com.example.Book_my_show.Controllers;

import com.example.Book_my_show.EntryDtos.MovieEntryDto;
import com.example.Book_my_show.Models.Movie;
import com.example.Book_my_show.ResponseDtos.MovieResponseDto;
import com.example.Book_my_show.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/movies")
public class MovieController{
    @Autowired
    MovieService movieService;
    @PostMapping("/add_movie")
    public String addMovie(@RequestBody MovieEntryDto movieEntryDto){

        return movieService.addMovie(movieEntryDto);
    }
    @GetMapping("/get_movie")
    public Movie getMovieById(@RequestParam int movieId){
           Movie movie= movieService.getMovieById(movieId);
           return movie;
    }
    @GetMapping("/get_all_movies")
    public List<MovieResponseDto> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/movie_by_maxShows")
    public MovieResponseDto getMovieByMaxShows(){
        return movieService.getMovieByMaxShows();
    }
    @GetMapping("/total_revenue")
    public long totalRevenue(@RequestParam String movieName){
        return movieService.totalRevenue(movieName);
    }
    @GetMapping("/rating")
    public double getRating(@RequestParam String movieName){
        return movieService.getRating(movieName);
    }
}
