package com.storage.events.listeners;

import com.storage.events.model.EmailVerificationMessage;
import com.storage.events.model.QuoteConfirmationMessage;
import com.storage.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailListener {
    private final EmailService emailService;

    @Async
    @EventListener
    public void sendQuoteConfirmation(final QuoteConfirmationMessage message){
        emailService.sendQuoteConfirmation(message.email());
    }

    @Async
    @EventListener
    public void sendEmailVerification(final EmailVerificationMessage message){
        emailService.sendEmailVerification(message.email());
    }
}
