package com.example.mslibrarymanagementsystem.service;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.LoanRequest;
import com.example.mslibrarymanagementsystem.dto.request.LoanSearchRequest;
import com.example.mslibrarymanagementsystem.dto.response.LoanResponse;
import com.example.mslibrarymanagementsystem.entity.BookEntity;
import com.example.mslibrarymanagementsystem.entity.LoanEntity;
import com.example.mslibrarymanagementsystem.enums.LoanStatus;
import com.example.mslibrarymanagementsystem.exceptions.*;
import com.example.mslibrarymanagementsystem.mapper.LoanMapper;
import com.example.mslibrarymanagementsystem.repository.BookRepository;
import com.example.mslibrarymanagementsystem.repository.LoanRepository;
import com.example.mslibrarymanagementsystem.repository.MemberRepository;
import com.example.mslibrarymanagementsystem.specification.LoanSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    @Transactional
    public LoanResponse createLoan(LoanRequest loanRequest) {
        var member = memberRepository.findById(loanRequest.getMemberId())
                .orElseThrow(() ->
                        new MemberNotFoundException(
                                "Member with id " + loanRequest.getMemberId() + " not found."));

        var book = bookRepository.findById(loanRequest.getBookId())
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book with id " + loanRequest.getBookId() + " not found."));

        validateLoanDates(loanRequest);
        validateBookAvailability(book);

        var loan = loanMapper.toEntity(loanRequest, member, book);
        book.setAvailable(false);
        var savedLoan = loanRepository.save(loan);
        return loanMapper.toResponse(savedLoan);
    }

    public Page<LoanResponse> getLoansWithDetails(PageCriteria pageCriteria) {
        Sort.Direction direction = Sort.Direction.fromString(pageCriteria.getDirection());

        var pageable = PageRequest.of(
                pageCriteria.getPage(),
                pageCriteria.getCount(),
                Sort.by(direction, pageCriteria.getSortBy())
        );

        Page<LoanEntity> loans = loanRepository.findAllWithDetails(pageable);
        return loans.map(loanMapper::toResponse);
    }

    public LoanResponse getLoanById(Long id) {
        var loan = fetchLoanIfExists(id);
        return loanMapper.toResponse(loan);
    }

    @Transactional
    public void returnLoan(Long id) {
        var loan = fetchLoanIfExists(id);

        if (isLoanReturned(id)) {
            throw new LoanAlreadyReturnedException("Loan is already returned");
        }

        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());

        loan.getBook().setAvailable(true);
    }

    public List<LoanResponse> getLoansByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidLoanDateException("Start date and end date must not be null.");
        }

        if (endDate.isBefore(startDate)) {
            throw new InvalidLoanDateException("End date cannot be before start date.");
        }

        return loanRepository.findLoansBetweenDates(startDate, endDate)
                .stream()
                .map(loanMapper::toResponse)
                .toList();
    }

    public List<LoanResponse> searchLoans(LoanSearchRequest request) {
        var specification = LoanSpecification.filter(request);

        return loanRepository.findAll(specification)
                .stream()
                .map(loanMapper::toResponse)
                .toList();
    }

    public List<LoanResponse> getAllWithDetails() {

        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toResponse)
                .toList();
    }

    public int processOverdueLoans() {
        List<LoanEntity> overdueLoans =
                loanRepository.findAllByStatusAndDueDateBefore(
                        LoanStatus.BORROWED,
                        LocalDate.now()
                );

        overdueLoans.forEach(loan ->
                log.warn(
                        "Overdue loan detected: loanId={}, bookId={}, memberId={}, dueDate={}",
                        loan.getId(),
                        loan.getBook().getId(),
                        loan.getMember().getId(),
                        loan.getDueDate()
                )
        );
        return overdueLoans.size();
    }

    private boolean isLoanReturned(Long loanId) {
        return loanRepository.isLoanAlreadyReturned(
                loanId,
                LoanStatus.RETURNED
        );
    }

    private LoanEntity fetchLoanIfExists(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan with id " + id + " not found."));
    }

    private void validateLoanDates(LoanRequest loanRequest) {
        if (loanRequest.getDueDate().isBefore(loanRequest.getLoanDate())) {
            throw new InvalidLoanDateException(
                    "Due date cannot be before loan date.");
        }
    }

    private void validateBookAvailability(BookEntity book) {
        if (loanRepository.existsByBookIdAndStatus(book.getId(), LoanStatus.BORROWED)) {
            throw new BookAlreadyBorrowedException(
                    "Book with id " + book.getId() + " is already borrowed.");
        }
    }
}