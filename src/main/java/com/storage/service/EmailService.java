package com.storage.service;

import com.storage.client.EmailClient;
import com.storage.models.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailClient emailClient;

    public void sendQuoteConfirmation(String email, String firstName, String lastName) {
        String subject = "Your Storage Space reservation";
        String body = """
                Hi %s
                                                        
                It means a lot to us that you have chosen Storage Space as your storage provider!
                We want you to know that we value your trust and will do everything we can to ensure 
                you have a positive experience with us. Our team is committed to maintaining 
                the highest standards and providing the best quality service to meet all your storage needs.
                                                        
                You can easily complete the check-in process by visiting our website %s. 
                It's a straightforward process, and you can save your progress and return 
                to it as many times as you need.
                                                                                
                """;

        EmailRequest emailRequest = new EmailRequest(
                email,
                String.format("%s %s ", firstName, lastName),
                subject,
                String.format(body, firstName, "www.once-upon-a-time")
        );

        emailClient.sendEmail(emailRequest);
    }
}
