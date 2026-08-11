package com.example.mslibrarymanagementsystem.controller;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.BookRequest;
import com.example.mslibrarymanagementsystem.dto.request.BookSearchRequest;
import com.example.mslibrarymanagementsystem.dto.request.LoanRequest;
import com.example.mslibrarymanagementsystem.dto.response.BookResponse;
import com.example.mslibrarymanagementsystem.exceptions.ErrorResponse;
import com.example.mslibrarymanagementsystem.service.BookService;
import com.example.mslibrarymanagementsystem.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final FileStorageService fileStorageService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
            summary = "Create book",
            description = "Creates a new book"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void createBook(@Valid @RequestBody BookRequest bookRequest) {
        bookService.createBook(bookRequest);
    }

    @GetMapping
    @Operation(
            summary = "Get all books",
            description = "Returns paginated list of books with author details while preventing N+1 query problems"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully")
    })
    public Page<BookResponse> getBooksWithDetails(PageCriteria pageCriteria) {
        return bookService.getBooksWithDetails(pageCriteria);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get book by id",
            description = "Returns book details by given id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public BookResponse getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload book cover",
            description = "Uploads a JPG or PNG cover image for a book. Maximum file size is 5MB"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book cover uploaded successfully"),
            @ApiResponse(
                    responseCode = "400", description = "Invalid file type or file size",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Book not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void uploadBookCover(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        fileStorageService.storeBookCover(id, file);
    }

    @GetMapping("/{id}/cover")
    @Operation(
            summary = "Download book cover",
            description = "Downloads the cover image of a book"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book cover downloaded successfully"),
            @ApiResponse(
                    responseCode = "404", description = "Book or cover image not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Resource> downloadBookCover(@PathVariable Long id) {
        Resource resource = fileStorageService.loadBookCover(id);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\""
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PutMapping("/{bookId}/borrow/{memberId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
            summary = "Borrow book",
            description = "Borrows a book for a member"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Book borrowed successfully"),
            @ApiResponse(
                    responseCode = "404", description = "Book or member not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409", description = "Book is already borrowed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void borrowBook(@PathVariable Long bookId,
                           @PathVariable Long memberId,
    @RequestBody LoanRequest loanRequest) {
        bookService.borrowBook(bookId, memberId, loanRequest);
    }

    @PutMapping("/{bookId}/return")
    @ResponseStatus(NO_CONTENT)
    @Operation(
            summary = "Return book",
            description = "Returns a borrowed book"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Book returned successfully"),
            @ApiResponse(
                    responseCode = "404", description = "Book not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409", description = "Book is already returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void returnBook(@PathVariable Long bookId) {
        bookService.returnBook(bookId);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update book",
            description = "Updates an existing book"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Book not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public BookResponse updateBook(@PathVariable Long id,
                                   @Valid @RequestBody BookRequest bookRequest) {
        return bookService.updateBook(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
            summary = "Delete book",
            description = "Deletes book by given id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search books",
            description = "Search books by optional filter criteria."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Books retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid search parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public List<BookResponse> searchBooks(BookSearchRequest request) {
        return bookService.searchBooks(request);
    }
}