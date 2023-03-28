package com.storage.controllers;

import com.storage.client.EmailClient;
import com.storage.models.EmailRequest;
import com.storage.models.businessObject.Quote;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.service.QuoteService;
import com.storage.service.UserService;
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
    private final UserService userService;
    private final EmailClient emailClient;

    @PostMapping("/estimation")
    public ResponseEntity<Quote> generateQuote(@RequestBody QuoteEstimateRequest request) {
        var estimationDTO = quoteService.estimate(request);
        // save user in db
        userService.saveNewCustomer(request);

        // send email to user
        EmailRequest emailRequest = EmailRequest.builder()
                .to(request.getEmail())
                .recipientName(String.format("%s %s ", request.getFirstName(), request.getLastName()))
                .subject("Your Storage Space reservation")
                .content(String.format("""
                        Hi %s
                                                                
                        It means a lot to us that you have chosen Storage Space as your storage provider!
                        We want you to know that we value your trust and will do everything we can to ensure 
                        you have a positive experience with us. Our team is committed to maintaining 
                        the highest standards and providing the best quality service to meet all your storage needs.
                                                                
                        You can easily complete the check-in process by visiting our website %s. 
                        It's a straightforward process, and you can save your progress and return 
                        to it as many times as you need.
                                                                                        
                        """, request.getFirstName(),"www.once-upon-a-time"))
                .build();
        emailClient.sendEmail(emailRequest);

        return new ResponseEntity<>(estimationDTO, HttpStatus.CREATED);
    }
}
