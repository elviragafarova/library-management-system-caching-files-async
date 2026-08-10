package com.example.mslibrarymanagementsystem.specification;

import com.example.mslibrarymanagementsystem.dto.request.BookSearchRequest;
import com.example.mslibrarymanagementsystem.entity.BookEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class BookSpecification {
    private BookSpecification() {
    }

    public static Specification<BookEntity> withFilters(BookSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getIsbn() != null && !request.getIsbn().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isbn")),
                                "%" + request.getIsbn().toLowerCase() + "%"
                        )
                );
            }

            if (request.getTitle() != null && !request.getTitle().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + request.getTitle().toLowerCase() + "%"
                        )
                );
            }

            if (request.getGenre() != null && !request.getGenre().isBlank()) {
                predicates.add(
                        cb.equal(
                                cb.lower(root.get("genre")),
                                request.getGenre().toLowerCase()
                        )
                );
            }

            if (request.getPublishedYear() != null) {
                predicates.add(
                        cb.equal(root.get("publishedYear"),
                                request.getPublishedYear())
                );
            }

            if (request.getIsAvailable() != null) {
                predicates.add(
                        cb.equal(root.get("isAvailable"),
                                request.getIsAvailable())
                );
            }

            if (request.getAuthorId() != null) {
                predicates.add(
                        cb.equal(root.get("author").get("id"),
                                request.getAuthorId())
                );
            }

            if (request.getBorrowedById() != null) {
                predicates.add(
                        cb.equal(root.get("borrowedBy").get("id"),
                                request.getBorrowedById())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}