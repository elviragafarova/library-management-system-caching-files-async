package com.example.mslibrarymanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Category response object")
public class CategoryResponse {
    @Schema(
            description = "Unique identifier of the category",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Category name",
            example = "Science Fiction"
    )
    private String name;
}