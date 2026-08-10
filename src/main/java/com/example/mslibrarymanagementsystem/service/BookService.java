package com.example.mslibrarymanagementsystem.service;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.BookRequest;
import com.example.mslibrarymanagementsystem.dto.request.BookSearchRequest;
import com.example.mslibrarymanagementsystem.dto.request.LoanRequest;
import com.example.mslibrarymanagementsystem.dto.response.BookResponse;
import com.example.mslibrarymanagementsystem.entity.AuthorEntity;
import com.example.mslibrarymanagementsystem.entity.BookEntity;
import com.example.mslibrarymanagementsystem.entity.MemberEntity;
import com.example.mslibrarymanagementsystem.exceptions.*;
import com.example.mslibrarymanagementsystem.mapper.BookMapper;
import com.example.mslibrarymanagementsystem.mapper.LoanMapper;
import com.example.mslibrarymanagementsystem.repository.AuthorRepository;
import com.example.mslibrarymanagementsystem.repository.BookRepository;
import com.example.mslibrarymanagementsystem.repository.LoanRepository;
import com.example.mslibrarymanagementsystem.repository.MemberRepository;
import com.example.mslibrarymanagementsystem.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final AuthorRepository authorRepository;
    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;

    public void createBook(BookRequest bookRequest) {
        var author = fetchAuthorIfExists(bookRequest.getAuthorId());
        var book = BookMapper.toEntity(bookRequest);

        book.setAuthor(author);
        book.setAvailable(true);

        bookRepository.save(book);
    }

    public Page<BookResponse> getBooksWithDetails(PageCriteria pageCriteria) {
        Sort.Direction direction = Sort.Direction.fromString(pageCriteria.getDirection());
        var pageable = PageRequest.of(
                pageCriteria.getPage(),
                pageCriteria.getCount(),
                Sort.by(direction, pageCriteria.getSortBy())
        );
        Page<BookEntity> books = bookRepository.findAllWithDetails(pageable);
        return books.map(BookMapper::toResponse);
    }

    @Cacheable(value = "books", key = "#id")
    public BookResponse getBookById(Long id) {
        log.info("Fetching book with id {} from database", id);
        var book = fetchBookIfExists(id);
        return BookMapper.toResponse(book);
    }

    public BookResponse updateBook(Long id, BookRequest request) {
        var book = fetchBookIfExists(id);
        var author = fetchAuthorIfExists(request.getAuthorId());
        BookMapper.updateBook(book, request);
        book.setAuthor(author);
        var updatedBook = bookRepository.save(book);
        return BookMapper.toResponse(updatedBook);
    }

    @Transactional
    public void borrowBook(Long bookId, Long memberId, LoanRequest loanRequest) {
        var book = fetchBookIfExists(bookId);
        var member = fetchMemberIfExists(memberId);

        if (!book.isAvailable()) {
            throw new BookAlreadyBorrowedException("Book is already borrowed.");
        }

        book.setBorrowedBy(member);
        book.setAvailable(false);

        var loan = loanMapper.toEntity(loanRequest, member, book);

        bookRepository.save(book);
        loanRepository.save(loan);
    }

    public void returnBook(Long bookId) {
        var book = fetchBookIfExists(bookId);
        if (book.isAvailable()) {
            throw new BookAlreadyReturnedException("Book is already returned.");
        }
        book.setBorrowedBy(null);
        book.setAvailable(true);
        var borrowedBook = bookRepository.save(book);
        BookMapper.toResponse(borrowedBook);
    }

    public void deleteBook(Long id) {
        var book = fetchBookIfExists(id);
        bookRepository.delete(book);
    }

    private BookEntity fetchBookIfExists(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    private MemberEntity fetchMemberIfExists(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + id));
    }

    private AuthorEntity fetchAuthorIfExists(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + id));
    }

    public List<BookResponse> searchBooks(
            String categoryName,
            Long authorId,
            String genre,
            Integer startYear,
            Integer endYear
    ) {

        List<BookEntity> books;

        if (categoryName != null && !categoryName.isBlank()) {
            books = bookRepository.findBooksByCategoryName(categoryName);

        } else if (authorId != null && genre != null && !genre.isBlank()) {
            books = bookRepository.findBooksByAuthorAndGenre(authorId, genre);

        } else if (startYear != null && endYear != null) {
            books = bookRepository.findByPublishedYearBetween(startYear, endYear);

        } else {
            throw new IllegalArgumentException(
                    "At least one filter must be provided: categoryName, (authorId and genre), or (startYear and endYear)."
            );
        }

        return books.stream()
                .map(BookMapper::toResponse)
                .toList();
    }

    public List<BookResponse> searchBooks(BookSearchRequest request) {

        var specification = BookSpecification.withFilters(request);

        return bookRepository.findAll(specification)
                .stream()
                .map(BookMapper::toResponse)
                .toList();
    }
}