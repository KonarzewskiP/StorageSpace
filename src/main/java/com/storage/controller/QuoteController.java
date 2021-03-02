package com.storage.controller;

import com.storage.model.Quote;
import com.storage.model.QuoteResponse;
import com.storage.model.dto.StorageRoomDto;
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
    public ResponseEntity<QuoteResponse> sendQuote(@RequestBody Quote quote) {
        log.info("Enter QuoteController -> sendQuote() with: " + quote);
        return new ResponseEntity<>(quoteService.sendQuote(quote), HttpStatus.OK);
    }
}
