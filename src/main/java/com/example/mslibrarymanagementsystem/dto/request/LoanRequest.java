package com.example.mslibrarymanagementsystem.dto.request;

import com.example.mslibrarymanagementsystem.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request object for creating or updating a loan")
public class LoanRequest {
    @Schema(
            description = "ID of the member borrowing the book",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Member id cannot be null")
    private Long memberId;

    @Schema(
            description = "ID of the borrowed book",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Book id cannot be null")
    private Long bookId;

    @Schema(
            description = "Date when the book was borrowed",
            example = "2026-08-02",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Loan date cannot be null")
    private LocalDate loanDate;

    @Schema(
            description = "Due date for returning the book",
            example = "2026-08-16",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;
}