package com.klu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String tribe;
    private String category;
    private String stateName;
    private String imageUrl;
    private Double price;
    private Integer stock;
    private Boolean customizable;

    @Column(length = 3000)
    private String description;

    @Column(length = 1000)
    private String materials;

    @Column(length = 1000)
    private String careInstructions;

    @ManyToOne
    @JoinColumn(name = "artisan_id")
    private User artisan;
}