package com.example.Book_my_show.ResponseDtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShowTimingsDto {
    LocalDate date;
    LocalTime time;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
