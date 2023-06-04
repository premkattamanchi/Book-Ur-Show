package com.example.Book_my_show.ResponseDtos;

import com.example.Book_my_show.Enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDto {
    private String name;
    private Language language;
    private double rating;
}
