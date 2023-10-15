package com.midas.store.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private double total;
    private LocalDateTime purchaseDate;
    private UserResponse userResponse;
    private List<ProductResponse> productResponses;

}
