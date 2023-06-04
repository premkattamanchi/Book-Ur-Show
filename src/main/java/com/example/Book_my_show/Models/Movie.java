package com.example.Book_my_show.Models;

import com.example.Book_my_show.Enums.Genre;
import com.example.Book_my_show.Enums.Language;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,nullable = false)
    private String name;
    private int duration;
    private double rating;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @Enumerated(value = EnumType.STRING)
    private Language language;
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Show> showList=new ArrayList<>();

}
