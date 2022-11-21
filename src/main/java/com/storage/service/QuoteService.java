package com.storage.service;

import com.storage.models.Warehouse;
import com.storage.models.businessObject.Quote;
import com.storage.models.dto.QuoteResponseDto;
import com.storage.models.enums.DeliveryStatus;
import com.storage.models.requests.QuoteEstimateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

//TODO create separate Email Service class
// integration with 3rd party Email API gateway?

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
    private final WarehouseService warehouseService;
    private final PriceService priceService;
    private final ModelMapper modelMapper;

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

    public QuoteResponseDto estimate(QuoteEstimateRequest estimate) {
        log.info("Start quote estimation with request: [{}]", estimate);
        estimate.isValid();

        var warehouse = warehouseService.findByUuid(estimate.getWarehouseUuid());

        BigDecimal price = priceService.calculatePrice(estimate);
        Quote quote = generateQuote(estimate, warehouse, price);

        return sendEmail(quote);
    }

    private Quote generateQuote(QuoteEstimateRequest estimate, Warehouse warehouse, BigDecimal price) {
        Quote quote = modelMapper.map(estimate, Quote.class);

        //TODO finish method

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
        String roomType = quote.getStorageSize().getDescription();
        String duration = quote.getDuration().getDuration();
        String warehouseName = quote.getWarehouseName();
//        BigDecimal price = quote.quote();

        return String.format("Hi %s, here is your price.\n\n" +
                        "Move-in: %s\nAnticipated stay: %s\nRoom size: %s sq ft\nRoom type: %s\n\n%s:\n£s.2ef per week.",
                firstName, date, duration, size, roomType, warehouseName);
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
