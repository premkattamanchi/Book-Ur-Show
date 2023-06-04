package com.example.Book_my_show.EntryDtos;

import lombok.Data;

@Data
public class TheatreEntryDto {
    private String name;
    private String location;
    private int noOfClassicSeats;
    private int noOfPremiumSeats;
}
