package com.example.mslibrarymanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response model for member")
public class MemberResponse {
    @Schema(
            description = "Member identifier",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Member's first name",
            example = "John"
    )
    private String firstName;

    @Schema(
            description = "Member's last name",
            example = "Doe"
    )
    private String lastName;

    @Schema(
            description = "Member's email address",
            example = "john.doe@example.com"
    )
    private String email;
}