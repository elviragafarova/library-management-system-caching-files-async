package com.example.mslibrarymanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request model for creating or updating a book")
public class BookRequest {
    @Schema(
            description = "International Standard Book Number",
            example = "9780132350884"
    )
    @NotBlank(message = "ISBN cannot be blank")
    @Pattern(
            regexp = "^(97(8|9))?\\d{9}(\\d|X)$",
            message = "Invalid ISBN format"
    )
    private String isbn;

    @Schema(
            description = "Book title",
            example = "Clean Code"
    )
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100)
    private String title;

    @Schema(
            description = "Year the book was published",
            example = "2008"
    )
    @NotNull(message = "Published year is required")
    @Positive(message = "Published year must be positive")
    private Integer publishedYear;

    @Schema(
            description = "Book genre",
            example = "Programming"
    )
    @NotBlank(message = "Genre cannot be blank")
    @Size(max = 50)
    private String genre;

    @Schema(
            description = "Identifier of the author",
            example = "1"
    )
    @NotNull(message = "Author id is required")
    private Long authorId;
}