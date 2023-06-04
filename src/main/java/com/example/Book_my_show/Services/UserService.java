package com.example.Book_my_show.Services;

import com.example.Book_my_show.EntryDtos.UserEntryDto;
import com.example.Book_my_show.Models.Show;
import com.example.Book_my_show.Models.Ticket;
import com.example.Book_my_show.Models.User;
import com.example.Book_my_show.Repositories.ShowRepository;
import com.example.Book_my_show.Repositories.UserRepository;
import com.example.Book_my_show.ResponseDtos.TicketResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShowRepository showRepository;
    public String addUser(UserEntryDto userEntryDto){
        //converting Dto to User Entity using @builder written top of the User entity
        User user=User.builder().name(userEntryDto.getName()).age(userEntryDto.getAge()).email(userEntryDto.getEmail()).
                mobile(userEntryDto.getMobile()).address(userEntryDto.getAddress()).build();
        userRepository.save(user);
        return "user added";
    }
    public List<TicketResDto> getAllTickets(int userId) throws Exception{
        User user=userRepository.findById(userId).get();
        List<Ticket> ticketList=user.getTicketList();
        List<TicketResDto> dtoList=new ArrayList<>();
        for(Ticket ticket:ticketList){
            TicketResDto ticketResDto= TicketResDto.builder().showDate(ticket.getShowDate()).
                    showTime(ticket.getShowTime()).bookedSeats(ticket.getBookedSeats()).
                    totalAmount(ticket.getTotalAmount()).build();
            dtoList.add(ticketResDto);
        }
        return dtoList;
    }
    public String updateMail(int userId,String mailId){
        User user=userRepository.findById(userId).get();
        user.setEmail(mailId);
        userRepository.save(user);
        return "Mail updated successfully";
    }
}
