package com.example.mslibrarymanagementsystem.mapper;

import com.example.mslibrarymanagementsystem.dto.request.AuthorRequest;
import com.example.mslibrarymanagementsystem.dto.response.AuthorResponse;
import com.example.mslibrarymanagementsystem.entity.AuthorEntity;

public class AuthorMapper {
    public static AuthorEntity toEntity(AuthorRequest authorRequest) {
        return AuthorEntity.builder()
                .name(authorRequest.getName())
                .build();
    }

    public static AuthorResponse toResponse(AuthorEntity authorEntity) {
        return AuthorResponse.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getName())
                .build();
    }
}