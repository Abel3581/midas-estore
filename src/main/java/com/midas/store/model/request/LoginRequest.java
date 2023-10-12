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
public class LoginRequest {

    @NotBlank(message = "El Email es requerido")
    private String username;
    @NotBlank(message = "El password es requerido")
    @Size(min = 8, max = 25, message = "El password debe tener entre 8 y 25 carcateres")
    private String password;
}
