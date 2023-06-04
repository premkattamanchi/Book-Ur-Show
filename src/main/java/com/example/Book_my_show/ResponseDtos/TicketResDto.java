package com.example.Book_my_show.ResponseDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResDto {
    private LocalDate showDate;
    private LocalTime showTime;
    private String bookedSeats;
    private int totalAmount;
}
