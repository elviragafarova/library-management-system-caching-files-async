package com.example.mslibrarymanagementsystem.controller;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.MemberRequest;
import com.example.mslibrarymanagementsystem.dto.response.BookResponse;
import com.example.mslibrarymanagementsystem.dto.response.MemberResponse;
import com.example.mslibrarymanagementsystem.exceptions.ErrorResponse;
import com.example.mslibrarymanagementsystem.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
            summary = "Create member",
            description = "Creates a new member"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Member created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void createMember(@Valid @RequestBody MemberRequest memberRequest) {
        memberService.createMember(memberRequest);
    }

    @GetMapping
    @Operation(
            summary = "Get all members",
            description = "Returns paginated list of members"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Members retrieved successfully")
    })
    public Page<MemberResponse> getAllMembers(PageCriteria pageCriteria) {
        return memberService.getMembers(pageCriteria);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get member by id",
            description = "Returns member details by given id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public MemberResponse getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @GetMapping("/{id}/books")
    @Operation(
            summary = "Get borrowed books",
            description = "Returns all books borrowed by a member"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Borrowed books retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public List<BookResponse> getBorrowedBooksByMemberId(@PathVariable Long id) {
        return memberService.getBorrowedBooksByMemberId(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update member",
            description = "Updates an existing member"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Member not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public MemberResponse updateMember(@PathVariable Long id,
                                       @Valid @RequestBody MemberRequest request) {
        return memberService.updateMember(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
            summary = "Delete member",
            description = "Deletes member by given id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}