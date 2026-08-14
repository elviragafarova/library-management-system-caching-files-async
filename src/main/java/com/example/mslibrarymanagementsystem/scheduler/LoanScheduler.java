package com.example.mslibrarymanagementsystem.scheduler;

import com.example.mslibrarymanagementsystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanScheduler {
    private final LoanService loanService;

    @Scheduled(cron = "${scheduler.overdue-loans.cron}")
    public void processOverdueLoans() {
        int overdueCount = loanService.processOverdueLoans();
        log.info("{} overdue loans detected", overdueCount);
    }
}