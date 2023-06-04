package com.example.Book_my_show.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data   // it contains getters,setters,toString(),RequiredArgsConstructor
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    @Column(unique = true,nullable = false)// it should not be null
    private String email;
    @NonNull// we can use nullable or NonNull
    @Column(unique = true)
    private String mobile;
    private String address;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Ticket> ticketList=new ArrayList<>();


}
