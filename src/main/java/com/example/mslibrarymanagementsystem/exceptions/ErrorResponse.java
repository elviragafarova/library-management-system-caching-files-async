package com.example.mslibrarymanagementsystem.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Standard API error response")
public class ErrorResponse {
    @Schema(
            description = "Application-specific error code",
            example = "author.not.found"
    )
    private String code;

    @Schema(
            description = "Detailed error message",
            example = "Author not found with id 1"
    )
    private String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}