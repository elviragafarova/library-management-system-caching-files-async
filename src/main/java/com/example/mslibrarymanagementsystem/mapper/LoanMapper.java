package com.example.mslibrarymanagementsystem.mapper;

import com.example.mslibrarymanagementsystem.dto.request.LoanRequest;
import com.example.mslibrarymanagementsystem.dto.response.LoanResponse;
import com.example.mslibrarymanagementsystem.entity.BookEntity;
import com.example.mslibrarymanagementsystem.entity.LoanEntity;
import com.example.mslibrarymanagementsystem.entity.MemberEntity;
import org.springframework.stereotype.Component;

import static com.example.mslibrarymanagementsystem.enums.LoanStatus.BORROWED;

@Component
public class LoanMapper {
    public LoanEntity toEntity(LoanRequest request, MemberEntity member, BookEntity book) {
        return LoanEntity.builder()
                .member(member)
                .book(book)
                .loanDate(request.getLoanDate())
                .dueDate(request.getDueDate())
                .status(BORROWED)
                .build();
    }

    public LoanResponse toResponse(LoanEntity entity) {
        return LoanResponse.builder()
                .id(entity.getId())
                .memberId(entity.getMember().getId())
                .bookId(entity.getBook().getId())
                .loanDate(entity.getLoanDate())
                .returnDate(entity.getReturnDate())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .build();
    }
}