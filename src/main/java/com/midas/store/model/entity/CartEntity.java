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
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double total = 0;
    @OneToOne(mappedBy = "cart")
    private UserEntity user;
    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductEntity> productEntities = new ArrayList<>();

    public void addProduct(ProductEntity productEntity){
        productEntities.add(productEntity);
        total = total + productEntity.getPrice();
    }
    public void clearCart(){
        productEntities.clear();
    }

}
