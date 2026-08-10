package com.example.mslibrarymanagementsystem.specification;

import com.example.mslibrarymanagementsystem.dto.request.LoanSearchRequest;
import com.example.mslibrarymanagementsystem.entity.LoanEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class LoanSpecification {
    private LoanSpecification() {
    }

    public static Specification<LoanEntity> filter(LoanSearchRequest request) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getMemberId() != null) {
                predicates.add(cb.equal(root.get("member").get("id"), request.getMemberId()));
            }

            if (request.getBookId() != null) {
                predicates.add(cb.equal(root.get("book").get("id"), request.getBookId()));
            }

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }

            if (request.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("loanDate"),
                        request.getStartDate()
                ));
            }

            if (request.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("loanDate"),
                        request.getEndDate()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}