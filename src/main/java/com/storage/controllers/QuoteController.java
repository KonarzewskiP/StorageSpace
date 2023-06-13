package com.storage.controllers;

import com.storage.events.publishers.EmailPublisher;
import com.storage.models.dto.QuoteDTO;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.service.QuoteService;
import com.storage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quote")
public class QuoteController {

    private final QuoteService quoteService;
    private final UserService userService;
    private final EmailPublisher emailPublisher;

    @PostMapping("/estimation")
    @Transactional
    public ResponseEntity<QuoteDTO> generateQuote(@RequestBody QuoteEstimateRequest request) {
        QuoteDTO quoteDTO = quoteService.estimate(request);

        // if email don't exist in DB, save a new user
        if (!userService.isEmailTaken(request.email()))
            userService.saveNewCustomer(request);

        // send confirmation email to user
        emailPublisher.publishQuoteConfirmationEvent(request.email());

        return new ResponseEntity<>(quoteDTO, HttpStatus.CREATED);
    }
}
