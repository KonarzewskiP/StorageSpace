package com.storage.service;

import com.storage.exceptions.QuoteDetailsException;
import com.storage.models.businessObject.Quote;
import com.storage.models.dto.QuoteResponseDto;
import com.storage.models.enums.DeliveryStatus;
import com.storage.validators.QuoteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

//TODO create separate Email Service class

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

    private static final String SUBJECT = "Your storage space quotation";

    /**
     * Method that takes details from Quote object and send email
     * with quotation to provided email address.
     *
     * @param quote
     * @return <code>QuoteResponse</code> object
     * @author Pawel Konarzewski
     * @since 02/03/2021
     */

    public QuoteResponseDto sendQuote(Quote quote) {
        log.info("Enter QuoteService -> sendQuote() with: " + quote);
        var validator = new QuoteValidator();
        var errors = validator.validate(quote);
        if (!errors.isEmpty()) {
            throw new QuoteDetailsException("Invalid Quote! errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
        return sendEmail(quote);
    }

    private SimpleMailMessage createSimpleMailMessage(String recipientAddress, String message) {
        log.info(String.format("Enter QuoteService -> sendEmail() with email: %s, message: %s", recipientAddress, message));
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipientAddress);
        simpleMailMessage.setSubject(SUBJECT);
        simpleMailMessage.setText(message);
        return simpleMailMessage;
    }

    private String createMessage(Quote quote) {
        String firstName = quote.getFirstName();
        var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd");
        String date = quote.getStartDate().format(dateFormatter);
        float size = quote.getStorageSize().getSizeInSqMeters();
        String roomType = quote.getStorageSize().getType();
        String duration = quote.getDuration().getDuration();
        String warehouseName = quote.getWarehouseName();
//        BigDecimal price = quote.quote();

        return String.format("Hi %s, here is your price.\n\n" +
                        "Move-in: %s\nAnticipated stay: %s\nRoom size: %s sq ft\nRoom type: %s\n\n%s:\n£%.2f per week.",
                firstName, date, duration, size, roomType, warehouseName, price);
    }

    private QuoteResponseDto sendEmail(Quote quote) {
        log.info("Enter QuoteService -> sendEmail() with: " + quote);
        var message = createMessage(quote);
        var recipientAddress = quote.getEmail();
        var quoteResponse =
                QuoteResponseDto.builder().email(quote.getEmail()).build();
        try {
            javaMailSender.send(createSimpleMailMessage(recipientAddress, message));
            quoteResponse.setStatus(DeliveryStatus.OK);
        } catch (Exception e) {
            quoteResponse.setStatus(DeliveryStatus.FAILED);
            log.error("Invalid Email! errors: " + e.getMessage());
        }
        return quoteResponse;
    }

}
