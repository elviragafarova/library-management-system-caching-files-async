package com.example.mslibrarymanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request object for searching books")
public class BookSearchRequest {
    @Schema(
            description = "Book ISBN",
            example = "9780134685991",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String isbn;

    @Schema(
            description = "Book title",
            example = "Effective Java",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String title;

    @Schema(
            description = "Book genre",
            example = "Programming",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String genre;

    @Schema(
            description = "Published year",
            example = "2018",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer publishedYear;

    @Schema(
            description = "Book availability",
            example = "true",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Boolean isAvailable;

    @Schema(
            description = "Author ID",
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long authorId;

    @Schema(
            description = "Member ID who borrowed the book",
            example = "2",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long borrowedById;
}