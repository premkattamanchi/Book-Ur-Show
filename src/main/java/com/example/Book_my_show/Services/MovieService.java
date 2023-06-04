package com.example.Book_my_show.Services;

import com.example.Book_my_show.EntryDtos.MovieEntryDto;
import com.example.Book_my_show.Models.Movie;
import com.example.Book_my_show.Models.Show;
import com.example.Book_my_show.Models.Ticket;
import com.example.Book_my_show.Repositories.MovieRepository;
import com.example.Book_my_show.Repositories.ShowRepository;
import com.example.Book_my_show.Repositories.TicketRepository;
import com.example.Book_my_show.ResponseDtos.MovieResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ShowRepository showRepository;
    @Autowired
    TicketRepository ticketRepository;
    public String addMovie(MovieEntryDto movieEntryDto){
        Movie movie=Movie.builder().name(movieEntryDto.getName()).genre(movieEntryDto.getGenre()).rating(movieEntryDto.getRating()).
                language(movieEntryDto.getLanguage()).duration(movieEntryDto.getDuration()).build();
        movieRepository.save(movie);
        return "movie added successfully";
    }
    public Movie getMovieById(int movieId){
        Movie movie=movieRepository.findById(movieId).get();
        return movie;
    }
    public List<MovieResponseDto> getAllMovies(){
        List<MovieResponseDto> movieResponseDtoList=new ArrayList<>();
        List<Movie> moviesList= movieRepository.findAll();
        for(Movie movie:moviesList){
            MovieResponseDto movieResponseDto=MovieResponseDto.builder()
                    .name(movie.getName())
                    .rating(movie.getRating())
                    .language(movie.getLanguage())
                    .build();
            movieResponseDtoList.add(movieResponseDto);
        }
        return movieResponseDtoList;
    }
    public MovieResponseDto getMovieByMaxShows() {
        MovieResponseDto movieResponseDto=new MovieResponseDto();
        List<Show> showList=showRepository.findAll();
        HashMap<Integer,Integer> map=new HashMap<>();
        Movie movie=null;
        int max=0;
        for(Show show:showList) {
            int movieId=show.getMovie().getId();
            map.put(movieId, map.getOrDefault(movieId, 0) + 1);
            if(map.get(movieId)>max){
                max=map.get(movieId);
                movie=show.getMovie();
            }
        }
        if(movie!=null){
            movieResponseDto.setName(movie.getName());
            movieResponseDto.setLanguage(movie.getLanguage());
            movieResponseDto.setRating(movie.getRating());
        }
        return movieResponseDto;
    }
    public long totalRevenue(String movieName){
        long revenue=0;
        List<Ticket> tickets=ticketRepository.findAll();
        for(Ticket ticket:tickets){
            Show show=ticket.getShow();
            if(movieName.equals(show.getMovie().getName()))
                revenue+=ticket.getTotalAmount();
        }
        return revenue;
    }
    public double getRating(String movieName){
        Movie movie=movieRepository.findByName(movieName);
        return movie.getRating();
    }
}
