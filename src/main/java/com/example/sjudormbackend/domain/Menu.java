package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "menu")
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "date")
    private String date;

    @Column(name = "lunch")
    private String lunch;

    @Column(name = "dinner")
    private String dinner;
}
