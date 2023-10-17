package com.midas.store.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int count;
    private boolean state;
    private int stock;


    public Product(String name, double price, int count, boolean state, int stock, String description) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
        this.state = state;
        this.stock = stock;
    }


}
