package com.leporonitech.products.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product{
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "product_id")
        Long id;
        @Column(name = "product_name")
        String name;
        @Column(name = "product_price")
        double price;
}
