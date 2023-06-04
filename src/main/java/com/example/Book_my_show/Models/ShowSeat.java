package com.example.Book_my_show.Models;

import com.example.Book_my_show.Enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "show_seats")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String seatNo;
    private SeatType seatType;
    private int price;
    private boolean isBooked;
    private Date bookedAt;

    @ManyToOne
    @JoinColumn
    private Show show;

}
