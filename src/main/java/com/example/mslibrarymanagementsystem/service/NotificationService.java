package com.example.mslibrarymanagementsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;


@Service
@Slf4j
public class NotificationService {

        @Async
        public void sendLoanNotification(
                String email,
                String bookTitle
        ) {

            log.info(
                    "Sending loan notification to {} for book {}",
                    email,
                    bookTitle
            );

            try {
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                log.error("Notification processing was interrupted", exception);
                return;
            }

            log.info(
                    "Loan notification successfully sent to {} for book {}",
                    email,
                    bookTitle
            );
        }
}