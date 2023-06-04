package com.example.Book_my_show.Controllers;

import com.example.Book_my_show.EntryDtos.UserEntryDto;
import com.example.Book_my_show.Models.Ticket;
import com.example.Book_my_show.ResponseDtos.TicketResDto;
import com.example.Book_my_show.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/add")
    public String addUser(@RequestBody UserEntryDto userEntryDto){
        return userService.addUser(userEntryDto);
    }
    @GetMapping("/all_tickets_booked")
    public List<TicketResDto> getAllTickets(@RequestParam int userId){
        List<TicketResDto> list=null;
        try{
            list=userService.getAllTickets(userId);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }
    @PutMapping("/update_mail")
    public String updateMail(@RequestParam int userId,@RequestParam String mailId){
        String ans="";
        try{
            ans=userService.updateMail(userId,mailId);
        }
        catch(Exception e){
            ans=e.getMessage();
        }
        return ans;
    }
}
