package com.storage.service;

import com.storage.model.Quote;
import com.storage.model.QuoteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Class that provides methods for sending email with quotation
 * for guest users
 *
 * @author Pawel Konarzewski
 * @since 02/03/2021
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuoteService {

    private final JavaMailSender javaMailSender;
    private final String EMAIL_FROM = "storagespacelondon@gmail.com";

    /**
     * Method that takes takes details from object Quote and send email
     * with quotation to guest user.
     *
     * @param quote
     * @return <code>Quote</code> object
     * @author Pawel Konarzewski
     * @since 02/03/2021
     */
    public Quote sendQuote(Quote quote) {
        log.info("Enter QuoteService -> sendQuote() with: " + quote);

        sendEmail(quote.getEmail(), "Test","StorageSpace");
        return quote;
    }

    private void sendEmail(String to, String body, String topic) {
        log.info(String.format("Enter QuoteService -> sendEmail() with email: %s, subject: %s\n" +
                "message: %s", to, topic, body));
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(EMAIL_FROM);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
    }
}
