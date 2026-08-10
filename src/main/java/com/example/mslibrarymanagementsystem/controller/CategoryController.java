package com.example.mslibrarymanagementsystem.controller;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.CategoryRequest;
import com.example.mslibrarymanagementsystem.dto.response.CategoryResponse;
import com.example.mslibrarymanagementsystem.exceptions.ErrorResponse;
import com.example.mslibrarymanagementsystem.service.CategoryService;
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

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
            summary = "Create category",
            description = "Creates a new category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "Category created successfully"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public void createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        categoryService.createCategory(categoryRequest);
    }

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Returns paginated list of categories"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Categories retrieved successfully"
            )
    })
    public Page<CategoryResponse> getCategories(
            PageCriteria pageCriteria
    ) {
        return categoryService.getCategories(pageCriteria);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get category by id",
            description = "Returns category details by given id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Category retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update category",
            description = "Updates an existing category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Category updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public CategoryResponse updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        return categoryService.updateCategory(id, request);
    }
}