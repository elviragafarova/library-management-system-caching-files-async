package com.example.mslibrarymanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response model for book")
public class BookResponse {
    @Schema(
            description = "Book identifier",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "International Standard Book Number",
            example = "9780132350884"
    )
    private String isbn;

    @Schema(
            description = "Book title",
            example = "Clean Code"
    )
    private String title;

    @Schema(
            description = "Book genre",
            example = "Programming"
    )
    private String genre;

    @Schema(
            description = "Year the book was published",
            example = "2008"
    )
    private Integer publishedYear;

    @Schema(
            description = "Author identifier",
            example = "1"
    )
    private Long authorId;
}