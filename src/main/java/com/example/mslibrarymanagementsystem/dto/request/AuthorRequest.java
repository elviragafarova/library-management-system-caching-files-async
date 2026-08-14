package com.example.mslibrarymanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request model for creating or updating an author")
public class AuthorRequest {
    @Schema(
            description = "Author's full name",
            example = "Robert C. Martin"
    )
    @NotBlank(message = "Name cannot be blank")
    private String name;
}