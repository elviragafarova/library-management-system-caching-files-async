package com.example.mslibrarymanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response model for author")
public class AuthorResponse {
    @Schema(
            description = "Author identifier",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Author's full name",
            example = "Robert C. Martin"
    )
    private String name;
}