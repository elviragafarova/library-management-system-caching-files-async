package com.example.mslibrarymanagementsystem.dto.request;

import com.example.mslibrarymanagementsystem.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request object for searching loans")
public class LoanSearchRequest {
    @Schema(
            description = "ID of the member who borrowed the book",
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long memberId;

    @Schema(
            description = "ID of the borrowed book",
            example = "10",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long bookId;

    @Schema(
            description = "Current loan status",
            example = "ACTIVE",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private LoanStatus status;

    @Schema(
            description = "Loan start date (inclusive)",
            example = "2026-08-01",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private LocalDate startDate;

    @Schema(
            description = "Loan end date (inclusive)",
            example = "2026-08-31",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private LocalDate endDate;
}