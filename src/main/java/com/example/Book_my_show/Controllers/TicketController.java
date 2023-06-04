package com.example.Book_my_show.Controllers;

import com.example.Book_my_show.EntryDtos.TicketEntryDto;
import com.example.Book_my_show.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/book_tickets")
    public String bookTickets(@RequestBody TicketEntryDto ticketEntryDto){
        try{
            return ticketService.bookTickets(ticketEntryDto);
        }
        catch(Exception e){
            return e.toString();
        }
    }
    @PutMapping("/cancel")
    public String CancelTickets(@RequestParam int userId,@RequestParam int ticketId){
        String ans="";
        try{
            ans=ticketService.cancelTickets(userId,ticketId);
        }
        catch(Exception e){
            ans=e.getMessage();
        }
        return ans;
    }

}
