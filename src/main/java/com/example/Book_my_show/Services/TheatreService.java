package com.example.Book_my_show.Services;

import com.example.Book_my_show.EntryDtos.TheatreEntryDto;
import com.example.Book_my_show.Enums.SeatType;
import com.example.Book_my_show.Models.Show;
import com.example.Book_my_show.Models.Theatre;
import com.example.Book_my_show.Models.TheatreSeat;
import com.example.Book_my_show.Repositories.ShowRepository;
import com.example.Book_my_show.Repositories.TheatreRepository;
import com.example.Book_my_show.ResponseDtos.TheatreResponseDto;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Service
public class TheatreService {
    @Autowired
    TheatreRepository theatreRepository;
    @Autowired
    ShowRepository showRepository;

    public String addTheatre(TheatreEntryDto theatreEntryDto) {
        Theatre theatre = new Theatre();
        theatre.setName(theatreEntryDto.getName());
        theatre.setLocation(theatreEntryDto.getLocation());

        List<TheatreSeat> theatreSeatList = theatre.getTheatreSeatList();
        //creating TheatreSeat entity and adding in the TheatreSeatList in parent Theatre
        for (int i = 1; i <= theatreEntryDto.getNoOfClassicSeats(); i++) {
            TheatreSeat theatreSeat = TheatreSeat.builder().seatType(SeatType.CLASSIC).seat_num(i + "C").theatre(theatre).build();
            theatreSeatList.add(theatreSeat);
        }
        for (int i = 1; i <= theatreEntryDto.getNoOfPremiumSeats(); i++) {
            TheatreSeat theatreSeat = TheatreSeat.builder().seatType(SeatType.PREMIUM).seat_num(i + "P").theatre(theatre).build();
            theatreSeatList.add(theatreSeat);
        }
        theatre.setTheatreSeatList(theatreSeatList);
        theatreRepository.save(theatre);
        return "Theatre added";
    }

    public List<String> getUniqueLocations(String theatreName) {
        List<String> locations = new ArrayList<>();
        List<Theatre> theatres = theatreRepository.findAll();
        HashSet<String> hs = new HashSet<>();
        for (Theatre theatre : theatres) {
            if (theatreName.equals(theatre.getName()))
                hs.add(theatre.getLocation());
        }
        for (String s : hs)
            locations.add(s);
        return locations;
    }
    public List<TheatreResponseDto> getTheatresByMovie(String movieName) {
        List<TheatreResponseDto> dtoList=new ArrayList<>();
        List<Show> showList=showRepository.findAll();
        HashSet<String> set=new HashSet<>();
        for(Show show:showList){
            if(movieName.equals(show.getMovie().getName()))
                set.add(show.getTheatre().getName());
        }
        for(String s:set){
            Theatre theatre=theatreRepository.findByName(s);
            TheatreResponseDto dto=new TheatreResponseDto();
            dto.setName(theatre.getName());
            dto.setLocation(theatre.getLocation());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
