package com.example.mslibrarymanagementsystem.mapper;

import com.example.mslibrarymanagementsystem.dto.request.BookRequest;
import com.example.mslibrarymanagementsystem.dto.response.BookResponse;
import com.example.mslibrarymanagementsystem.entity.BookEntity;

public class BookMapper {
    public static BookEntity toEntity(BookRequest bookRequest) {
        return BookEntity.builder()
                .isbn(bookRequest.getIsbn())
                .title(bookRequest.getTitle())
                .genre(bookRequest.getGenre())
                .publishedYear(bookRequest.getPublishedYear())
                .build();
    }

    public static BookResponse toResponse(BookEntity bookEntity) {
        return BookResponse.builder()
                .id(bookEntity.getId())
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .publishedYear(bookEntity.getPublishedYear())
                .genre(bookEntity.getGenre())
                .authorId(bookEntity.getAuthor().getId())
                .build();
    }

    public static void updateBook(BookEntity book, BookRequest request) {
        book.setIsbn(request.getIsbn());
        book.setTitle(request.getTitle());
        book.setGenre(request.getGenre());
        book.setPublishedYear(request.getPublishedYear());
    }
}