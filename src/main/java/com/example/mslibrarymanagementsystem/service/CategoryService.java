package com.example.mslibrarymanagementsystem.service;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.CategoryRequest;
import com.example.mslibrarymanagementsystem.dto.response.CategoryResponse;
import com.example.mslibrarymanagementsystem.entity.CategoryEntity;
import com.example.mslibrarymanagementsystem.exceptions.CategoryAlreadyExistsException;
import com.example.mslibrarymanagementsystem.exceptions.CategoryNotFoundException;
import com.example.mslibrarymanagementsystem.mapper.CategoryMapper;
import com.example.mslibrarymanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void createCategory(CategoryRequest categoryRequest) {
        validateCategoryName(categoryRequest.getName());
        var category = categoryMapper.toEntity(categoryRequest);
        categoryRepository.save(category);
    }

    public Page<CategoryResponse> getCategories(PageCriteria pageCriteria) {
        Sort.Direction direction = Sort.Direction.fromString(pageCriteria.getDirection());

            var pageable = PageRequest.of(
                    pageCriteria.getPage(),
                    pageCriteria.getCount(),
                    Sort.by(direction, pageCriteria.getSortBy())
            );

        Page<CategoryEntity> loans = categoryRepository.findAll(pageable);
        return loans.map(categoryMapper::toResponse);
    }

    public CategoryResponse getCategoryById(Long id) {
        var category = fetchCategoryIfExists(id);
        return categoryMapper.toResponse(category);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        validateCategoryName(categoryRequest.getName());
        var category = fetchCategoryIfExists(id);
        category.setName(categoryRequest.getName());
        return categoryMapper.toResponse(category);
    }

    private CategoryEntity fetchCategoryIfExists(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category with id " + id + " not found."));
    }

    private void validateCategoryName(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new CategoryAlreadyExistsException(
                    "Category with name '" + name + "' already exists.");
        }
    }
}