package com.example.Book_my_show.Services;

import com.example.Book_my_show.EntryDtos.TicketEntryDto;
import com.example.Book_my_show.Models.Show;
import com.example.Book_my_show.Models.ShowSeat;
import com.example.Book_my_show.Models.Ticket;
import com.example.Book_my_show.Models.User;
import com.example.Book_my_show.Repositories.ShowRepository;
import com.example.Book_my_show.Repositories.TicketRepository;
import com.example.Book_my_show.Repositories.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    ShowRepository showRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    JavaMailSender javaMailSender;
    public String bookTickets(TicketEntryDto ticketEntryDto) throws Exception{
        Show show=showRepository.findById(ticketEntryDto.getShowId()).get();
        User user=userRepository.findById(ticketEntryDto.getUserId()).get();
        //setting attributes of ticket entity
        Ticket ticket=Ticket.builder().showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .movieName(show.getMovie().getName())
                .theatreName(show.getTheatre().getName()).user(user).show(show).ticketId(UUID.randomUUID().toString())
                .build();
        boolean isValidRequest=availabilityOfRequestedSeats(ticketEntryDto);
        if(!isValidRequest)
            throw new Exception("seat not available");
        //else we can assume all seats are available to book
        int totalCost=0;
        List<String> requestedSeats=ticketEntryDto.getRequestedSeats();
        List<ShowSeat> showSeatList=show.getShowSeatList();
        for(ShowSeat showSeat:showSeatList){
            if(requestedSeats.contains(showSeat.getSeatNo()))
            {
                totalCost+=showSeat.getPrice();
                showSeat.setBooked(true);
                showSeat.setBookedAt(new Date());
            }
        }
        ticket.setTotalAmount(totalCost);
        //adding booked tickets in ticket entity
        String ticketsBooked=getAllotedSeatsfromShowSeats(requestedSeats);
        ticket.setBookedSeats(ticketsBooked);

        //saving Ticket else it will be saved twice because of parents
        ticket=ticketRepository.save(ticket);
        //adding in parents lists

        show.getTicketList().add(ticket);
        showRepository.save(show);

        user.getTicketList().add(ticket);
        userRepository.save(user);

        String body = "Hi this is to confirm your booking for seat No "+ticketsBooked+"for the movie : " + ticket.getMovieName();


        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("premkattamanchi@gmail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject("Confirming your booked Ticket");

        javaMailSender.send(mimeMessage);
        return "Ticket has been added Successfully";
    }
    public boolean availabilityOfRequestedSeats(TicketEntryDto ticketEntryDto){
        //to ckeck whether requested seats are available or not
        Show show=showRepository.findById(ticketEntryDto.getShowId()).get();
        List<String> requestedSeats=ticketEntryDto.getRequestedSeats();
        List<ShowSeat> showSeatList=show.getShowSeatList();
        for(ShowSeat showSeat:showSeatList){
            if(requestedSeats.contains(showSeat.getSeatNo()))
            {
                if(showSeat.isBooked())
                    return false;
            }
        }
        return true;
    }
    private String getAllotedSeatsfromShowSeats(List<String> requestedSeats){

        String result = "";

        for(String seat :requestedSeats){

            result = result+seat +",";

        }
        return result.substring(0,result.length()-1);

    }
    public String cancelTickets(int userId,int ticketId) throws Exception{
        User user=userRepository.findById(userId).get();
        Ticket ticket=ticketRepository.findById(ticketId).get();
        String bookedSeats=ticket.getBookedSeats();
        List<ShowSeat> showSeatList=ticket.getShow().getShowSeatList();
        int totalCost=ticket.getTotalAmount();
        for(ShowSeat showSeat:showSeatList){
            if(bookedSeats.contains(showSeat.getSeatNo()))
            {
                if(showSeat.isBooked()){
                    showSeat.setBooked(false);
                    totalCost-=showSeat.getPrice();
                }
            }
        }
        ticket.setTotalAmount(totalCost);
        userRepository.save(user);
        return "Tickets "+bookedSeats+" cancelled";
    }
}
