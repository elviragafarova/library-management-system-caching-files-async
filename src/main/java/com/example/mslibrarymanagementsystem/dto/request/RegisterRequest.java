package com.example.mslibrarymanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request model for user registration")
public class RegisterRequest {
    @Schema(
            description = "User email address",
            example = "john.doe@example.com"
    )
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must be at most 255 characters")
    private String email;

    @Schema(
            description = "User first name",
            example = "John"
    )
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @Schema(
            description = "User surname",
            example = "Doe"
    )
    @NotBlank(message = "Surname cannot be blank")
    @Size(max = 100, message = "Surname must be at most 100 characters")
    private String surname;

    @Schema(
            description = "User password",
            example = "Password123"
    )
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Schema(
            description = "Phone number in Azerbaijan format",
            example = "+994 50 123 45 67"
    )
    @NotBlank(message = "Phone number cannot be blank")
    @Size(max = 20, message = "Phone number must be at most 20 characters")
    @Pattern(
            regexp = "^\\+994 (12|50|51|55|70|77|99) \\d{3} \\d{2} \\d{2}$",
            message = "Phone number must be in the format: +994 XX XXX XX XX"
    )
    private String phoneNumber;
}