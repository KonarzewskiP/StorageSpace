package com.storage.controllers;

import com.storage.models.Quote;
import com.storage.models.dto.QuoteResponseDto;
import com.storage.service.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping
    public ResponseEntity<QuoteResponseDto> sendQuote(@RequestBody Quote quote) {
        log.info("Enter QuoteController -> sendQuote() with: " + quote);
        return new ResponseEntity<>(quoteService.sendQuote(quote), HttpStatus.OK);
    }
}
