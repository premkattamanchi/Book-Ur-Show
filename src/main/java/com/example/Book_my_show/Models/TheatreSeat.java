package com.example.Book_my_show.Models;

import com.example.Book_my_show.Enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theatre_seats")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheatreSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String seat_num;
    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;

    //unidirectional mapping with parent Theatre
    @ManyToOne
    @JoinColumn
    private Theatre theatre;

}
