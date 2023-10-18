package com.midas.store.controller;

import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.request.LoginRequest;
import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.LoginResponse;
import com.midas.store.model.response.RegisterResponse;
import com.midas.store.service.injectionDependency.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registrar Usuario", description = "registra un usuario CUSTOMER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro exitoso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponse.class))}),
            @ApiResponse(responseCode = "404", description = "El Cliente ya esta registrado",
                    content = @Content)})
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Iniciar session", description = "Loguea un usuario ADMIN o CUSTOMER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logueo exitoso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))}),
            @ApiResponse(responseCode = "400", description = "El password debe tener entre 8 y 25 carcateres",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No hay ninguna cuenta asociada con la dirección de correo electrónico.",
                    content = @Content)})
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
