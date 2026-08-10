package com.example.mslibrarymanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request model for creating or updating a member")
public class MemberRequest {
    @Schema(
            description = "Member's first name",
            example = "John"
    )
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50)
    private String firstName;

    @Schema(
            description = "Member's last name",
            example = "Doe"
    )
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50)
    private String lastName;

    @Schema(
            description = "Member's email address",
            example = "john.doe@example.com"
    )
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
}