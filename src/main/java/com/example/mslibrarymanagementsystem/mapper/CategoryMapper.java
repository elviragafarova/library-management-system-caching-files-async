package com.example.mslibrarymanagementsystem.mapper;

import com.example.mslibrarymanagementsystem.dto.request.CategoryRequest;
import com.example.mslibrarymanagementsystem.dto.response.CategoryResponse;
import com.example.mslibrarymanagementsystem.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryEntity toEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .name(request.getName())
                .build();
    }

    public CategoryResponse toResponse(CategoryEntity entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}