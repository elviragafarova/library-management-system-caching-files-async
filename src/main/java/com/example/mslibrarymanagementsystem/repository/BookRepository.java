package com.example.mslibrarymanagementsystem.repository;

import com.example.mslibrarymanagementsystem.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long>, JpaSpecificationExecutor<BookEntity> {
    @Query("""
    SELECT b
    FROM BookEntity b
    JOIN b.categories c
    WHERE c.name = :categoryName
""")
    List<BookEntity> findBooksByCategoryName(String categoryName);

    @Query("""
    SELECT b
    FROM BookEntity b
    WHERE b.author.id = :authorId
      AND LOWER(b.genre) = LOWER(:genre)
""")
    List<BookEntity> findBooksByAuthorAndGenre(Long authorId, String genre);

    List<BookEntity> findByPublishedYearBetween(
            Integer startYear,
            Integer endYear
    );

    @EntityGraph(attributePaths = {"author"})
    @Query("""
        SELECT b
        FROM BookEntity b
        """)
    Page<BookEntity> findAllWithDetails(Pageable pageable);
}