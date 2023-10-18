package com.midas.store.controller;

import com.midas.store.model.response.ProductResponse;
import com.midas.store.model.response.UserResponse;
import com.midas.store.service.injectionDependency.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Devuelve los usuarios", description = "Devuelve todos los usuarios del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))}),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. No tienes permiso para realizar esta acción.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
