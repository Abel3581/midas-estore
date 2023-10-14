package com.midas.store.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateResponse {

    private String message;
    private String name;
    private String description;
    private double price;
    private int count;
    private boolean state;
    private int stock;
}
