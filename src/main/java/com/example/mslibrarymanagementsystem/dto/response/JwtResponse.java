package com.example.mslibrarymanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JWT authentication response")
public class JwtResponse {
    @Schema(
            description = "JWT access token used for authentication",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSJ9.signature"
    )
    private String accessToken;

    @Schema(
            description = "Type of token",
            example = "Bearer"
    )
    private String tokenType;
}