package com.storage.events.publishers;

import com.storage.events.model.EmailVerificationMessage;
import com.storage.events.model.QuoteConfirmationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishEmailVerificationEvent(final String email) {
        EmailVerificationMessage message = new EmailVerificationMessage(email);
        publisher.publishEvent(message);
    }

    public void publishQuoteConfirmationEvent(final String email) {
        QuoteConfirmationMessage message = new QuoteConfirmationMessage(email);
        publisher.publishEvent(message);
    }
}
