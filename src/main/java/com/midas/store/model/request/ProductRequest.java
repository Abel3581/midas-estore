package com.midas.store.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "La descripcion es requerida")
    private String description;
    @NotBlank(message = "El precio es requerido")
    private double price;
    @Size(min = 0, max = 100, message = "La cantidad debe ser >= 0 and <= 100")
    private int count;
    private boolean state;
    @Size(min = 0, max = 1000, message = "Las reservas deben ser >= 0 and <= 1000")
    private int stock;
}
