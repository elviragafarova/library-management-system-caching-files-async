package com.example.mslibrarymanagementsystem.repository;

import com.example.mslibrarymanagementsystem.entity.CategoryEntity;
import com.example.mslibrarymanagementsystem.entity.LoanEntity;
import com.example.mslibrarymanagementsystem.enums.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long>, JpaSpecificationExecutor<LoanEntity> {
    @Query("""
                SELECT l
                FROM LoanEntity l
                WHERE l.loanDate BETWEEN :startDate AND :endDate
            """)
    List<LoanEntity> findLoansBetweenDates(
            LocalDate startDate,
            LocalDate endDate
    );

    boolean existsByBookIdAndStatus(Long bookId, LoanStatus status);

    @Query("""
    SELECT CASE
        WHEN COUNT(l) > 0 THEN true
        ELSE false
    END
    FROM LoanEntity l
    WHERE l.id = :loanId
      AND l.status = :status
""")
    boolean isLoanAlreadyReturned(Long loanId, LoanStatus status);

    @EntityGraph(attributePaths = {"member", "book"})
    @Query("""
        SELECT l
        FROM LoanEntity l
        """)
    Page<LoanEntity> findAllWithDetails(Pageable pageable);

    List<LoanEntity> findAllByStatusAndDueDateBefore(
            LoanStatus status,
            LocalDate date
    );
}