package com.example.mslibrarymanagementsystem.entity;

import com.example.mslibrarymanagementsystem.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "loans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate loanDate;

    private LocalDate returnDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Enumerated(STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;
}