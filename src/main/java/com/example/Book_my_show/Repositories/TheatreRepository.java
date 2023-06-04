package com.example.Book_my_show.Repositories;

import com.example.Book_my_show.Models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Integer> {
    Theatre findByName(String theatreName);
}
