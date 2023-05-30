package com.storage.controllers;

import com.storage.models.businessObject.Quote;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.service.EmailService;
import com.storage.service.QuoteService;
import com.storage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final EmailService emailService;

    @PostMapping("/estimation")
    public ResponseEntity<Quote> generateQuote(@RequestBody QuoteEstimateRequest request) {
        Quote quote = quoteService.estimate(request);

        // if email don't exist in DB, save a new user
        if (!userService.isEmailTaken(request.email()))
            userService.saveNewCustomer(request);

        // send confirmation to user
        emailService.sendQuoteConfirmation(request.email(), request.firstName(), request.lastName());

        return new ResponseEntity<>(quote, HttpStatus.CREATED);
    }
}
