package com.example.Book_my_show.Services;

import com.example.Book_my_show.EntryDtos.ShowEntryDto;
import com.example.Book_my_show.Enums.SeatType;
import com.example.Book_my_show.Models.*;
import com.example.Book_my_show.Repositories.MovieRepository;
import com.example.Book_my_show.Repositories.ShowRepository;
import com.example.Book_my_show.Repositories.TheatreRepository;
import com.example.Book_my_show.ResponseDtos.ShowTimingsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {
    @Autowired
    ShowRepository showRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TheatreRepository theatreRepository;
    public String addShow(ShowEntryDto showEntryDto){
        Theatre theatre=theatreRepository.findById(showEntryDto.getTheaterId()).get();
        Movie movie=movieRepository.findById(showEntryDto.getMovieId()).get();
        Show show=Show.builder().showDate(showEntryDto.getLocalDate()).showTime(showEntryDto.getLocalTime()).showType(showEntryDto.getShowType()).
                movie(movie).theatre(theatre).build();
        //creating showseat entity and adding in show's showseatlist
        List<ShowSeat> showSeatList=createShowSeatList(show,theatre,showEntryDto);
        show.setShowSeatList(showSeatList);
        //adding show in Movie's showList
        movie.getShowList().add(show);
        //adding show in Theatre's showList
        theatre.getShowList().add(show);
        showRepository.save(show);
        //if we dont save show entity it will be saved twice due to parents Theatre and Movie;
        theatreRepository.save(theatre);
        movieRepository.save(movie);
        return "Show Added Successfully";
    }
    public List<ShowSeat> createShowSeatList(Show show,Theatre theatre,ShowEntryDto showEntryDto){
        //copying attributes from theatreSeatList to ShowSeatList
        int classicSeatPrice=showEntryDto.getClassicSeatPrice();
        int premiumSeatPrice= showEntryDto.getPremiumSeatPrice();
        List<ShowSeat> showSeatList=new ArrayList<>();
        List<TheatreSeat> theatreSeatList=theatre.getTheatreSeatList();
        for(TheatreSeat ts:theatreSeatList){
            ShowSeat showSeat=ShowSeat.builder().
                    seatNo(ts.getSeat_num()).
                    seatType(ts.getSeatType()).
                    isBooked(false).bookedAt(null).show(show).build();
               if(ts.getSeatType().equals(SeatType.CLASSIC))
                   showSeat.setPrice(classicSeatPrice);
               else
                   showSeat.setPrice(premiumSeatPrice);
               showSeatList.add(showSeat);
        }
        return showSeatList;
    }
    public List<ShowTimingsDto> showTimings(String movieName,String theatreName){
        List<Show> showList=showRepository.findAll();
        List<ShowTimingsDto> showTimingsList=new ArrayList<>();
        for(Show show:showList){
            if(show.getMovie().getName().equals(movieName) && show.getTheatre().getName().equals(theatreName)){
                if(show.getShowTime().compareTo(LocalTime.now())>=0 && show.getShowDate().compareTo(LocalDate.now())>=0){
                    ShowTimingsDto showTimingsDto=new ShowTimingsDto();
                    showTimingsDto.setTime(show.getShowTime());
                    showTimingsDto.setDate(show.getShowDate());
                    showTimingsList.add(showTimingsDto);
                }
            }
        }
        return showTimingsList;
    }
}
