package com.example.mslibrarymanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request model for user login")
public class LoginRequest {
    @Schema(
            description = "User email address",
            example = "john.doe@example.com"
    )
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    String email;

    @Schema(
            description = "User password",
            example = "Password123"
    )
    @NotBlank(message = "Password cannot be blank")
    String password;
}