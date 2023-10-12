package com.midas.store.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double total;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

}
