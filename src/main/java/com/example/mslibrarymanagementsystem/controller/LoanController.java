package com.example.mslibrarymanagementsystem.controller;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.LoanRequest;
import com.example.mslibrarymanagementsystem.dto.request.LoanSearchRequest;
import com.example.mslibrarymanagementsystem.dto.response.LoanResponse;
import com.example.mslibrarymanagementsystem.exceptions.ErrorResponse;
import com.example.mslibrarymanagementsystem.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {
  private final LoanService loanService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
            summary = "Create loan",
            description = "Creates a new loan for a member and a book"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "Loan created successfully"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
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
    public LoanResponse createLoan(@Valid @RequestBody LoanRequest request) {
        return loanService.createLoan(request);
    }

    @GetMapping
    @Operation(
            summary = "Get all loans",
            description = "Returns paginated list of loans with member and book details while preventing N+1 query problems"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Loans retrieved successfully"
            )
    })
    public Page<LoanResponse> getLoansWithDetails(PageCriteria pageCriteria) {
        return loanService.getLoansWithDetails(pageCriteria);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get loan by id",
            description = "Returns loan details by given id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Loan retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Loan not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public LoanResponse getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @PutMapping("/{id}/return")
    @ResponseStatus(NO_CONTENT)
    @Operation(
            summary = "Return book",
            description = "Returns a borrowed book and updates loan status"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204", description = "Book returned successfully"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Loan not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409", description = "Loan is already returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void returnLoan(@PathVariable Long id) {
        loanService.returnLoan(id);
    }

    @GetMapping("/date-range")
    @Operation(
            summary = "Get loans by date range",
            description = "Returns all loans whose loan date is between the given start and end dates"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Loans retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Invalid date range",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public List<LoanResponse> getLoansByDateRange(
            @RequestParam
            @Schema(
                    description = "Start date of the loan period",
                    example = "2026-08-01"
            )
            LocalDate startDate,

            @RequestParam
            @Schema(
                    description = "End date of the loan period",
                    example = "2026-08-31"
            )
            LocalDate endDate) {

        return loanService.getLoansByDateRange(startDate, endDate);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search loans",
            description = "Searches loans by optional filter criteria such as member, book, status, and loan date range."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Loans retrieved successfully"
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
    public List<LoanResponse> searchLoans(LoanSearchRequest request) {
        return loanService.searchLoans(request);
    }
}