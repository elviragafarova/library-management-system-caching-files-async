package com.example.mslibrarymanagementsystem.dto.response;

import com.example.mslibrarymanagementsystem.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response object for loan")
public class LoanResponse {
    @Schema(
            description = "Loan ID",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "ID of the member borrowing the book",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long memberId;

    @Schema(
            description = "ID of the borrowed book",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long bookId;

    @Schema(
            description = "Date when the book was borrowed",
            example = "2026-08-02"
    )
    private LocalDate loanDate;

    @Schema(
            description = "Date when the book was returned",
            example = "2026-08-10"
    )
    private LocalDate returnDate;

    @Schema(
            description = "Due date for returning the book",
            example = "2026-08-16"
    )
    private LocalDate dueDate;

    @Schema(
            description = "Current loan status",
            example = "BORROWED"
    )
    private LoanStatus status;
}