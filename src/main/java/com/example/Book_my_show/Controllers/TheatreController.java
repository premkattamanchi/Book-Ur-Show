package com.example.Book_my_show.Controllers;

import com.example.Book_my_show.EntryDtos.TheatreEntryDto;
import com.example.Book_my_show.ResponseDtos.TheatreResponseDto;
import com.example.Book_my_show.Services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatre")
public class TheatreController {
    @Autowired
    TheatreService theatreService;
    @PostMapping("/add_theatre")
    public String addTheatre(@RequestBody TheatreEntryDto theatreEntryDto){
        return theatreService.addTheatre(theatreEntryDto);
    }
    @GetMapping("/unique_locations")
    public List<String> getUniqueLocations(String theatreName){
        return theatreService.getUniqueLocations(theatreName);
    }
    @GetMapping("/theatres_by_movieName")
    public List<TheatreResponseDto> getTheatresByMovie(String movieName){
        return theatreService.getTheatresByMovie(movieName);
    }
}
