package com.example.mslibrarymanagementsystem.controller;

import com.example.mslibrarymanagementsystem.dto.request.LoginRequest;
import com.example.mslibrarymanagementsystem.dto.request.RegisterRequest;
import com.example.mslibrarymanagementsystem.dto.response.JwtResponse;
import com.example.mslibrarymanagementsystem.exceptions.ErrorResponse;
import com.example.mslibrarymanagementsystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    @Operation(
            summary = "Register user",
            description = "Registers a new user and returns JWT token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "User registered successfully"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409", description = "Email already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public JwtResponse registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login user",
            description = "Authenticates user and returns JWT token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Login successful"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401", description = "Invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}