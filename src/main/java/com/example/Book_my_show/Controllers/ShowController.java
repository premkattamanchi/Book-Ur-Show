package com.example.Book_my_show.Controllers;

import com.example.Book_my_show.EntryDtos.ShowEntryDto;
import com.example.Book_my_show.ResponseDtos.ShowTimingsDto;
import com.example.Book_my_show.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {
    @Autowired
    ShowService showService;
    @PostMapping("/add_show")
    public String addShow(@RequestBody ShowEntryDto showEntryDto){
        return showService.addShow(showEntryDto);
    }

    @GetMapping("/get_show_time")
    public List<ShowTimingsDto> showTimings(@RequestParam String movieName,@RequestParam String theatreName){
        return showService.showTimings(movieName,theatreName);
    }
}
