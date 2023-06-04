package com.example.Book_my_show.EntryDtos;

import com.example.Book_my_show.Enums.Genre;
import com.example.Book_my_show.Enums.Language;
import lombok.Data;

@Data
public class MovieEntryDto {
    private String name;
    private int duration;
    private double rating;
    private Genre genre;
    private Language language;
}
