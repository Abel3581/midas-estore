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
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int count;
    private boolean state;
    private int stock;


    public ProductEntity(String name, double price, int count, boolean state, int stock, String description) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
        this.state = state;
        this.stock = stock;
    }

    public ProductEntity(long id, String name, double price, int count, boolean state, int stock, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.state = state;
        this.stock = stock;
        this.description = description;
    }


}
