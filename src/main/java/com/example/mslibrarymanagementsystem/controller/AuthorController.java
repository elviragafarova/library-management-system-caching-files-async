package com.example.mslibrarymanagementsystem.controller;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.AuthorRequest;
import com.example.mslibrarymanagementsystem.dto.response.AuthorResponse;
import com.example.mslibrarymanagementsystem.exceptions.ErrorResponse;
import com.example.mslibrarymanagementsystem.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
            summary = "Create author",
            description = "Creates a new author"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "Author created successfully"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void createAuthor(@Valid @RequestBody AuthorRequest authorRequest) {
        authorService.createAuthor(authorRequest);
    }

    @GetMapping
    @Operation(
            summary = "Get all authors",
            description = "Returns paginated list of authors"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Authors retrieved successfully"
            )
    })
    public Page<AuthorResponse> getAuthors(PageCriteria pageCriteria) {
        return authorService.getAuthors(pageCriteria);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get author by id",
            description = "Returns author details by given id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Author retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Author not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public AuthorResponse getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update author",
            description = "Updates an existing author"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Author updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Author not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public AuthorResponse updateAuthor(@PathVariable Long id,
                                       @Valid @RequestBody AuthorRequest request) {
        return authorService.updateAuthor(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
            summary = "Delete author",
            description = "Deletes author by given id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204", description = "Author deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Author not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}