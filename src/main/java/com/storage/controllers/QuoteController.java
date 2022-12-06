package com.storage.controllers;

import com.storage.models.businessObject.Quote;
import com.storage.models.requests.QuoteEstimateRequest;
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

    @PostMapping("/estimation")
    public ResponseEntity<Quote> sendQuote(@RequestBody QuoteEstimateRequest estimation) {
        var estimationDTO = quoteService.estimate(estimation);

        return new ResponseEntity<>(estimationDTO, HttpStatus.CREATED);
    }
}
