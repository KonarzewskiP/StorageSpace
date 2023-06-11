package com.storage.service;

import com.storage.client.EmailClient;
import com.storage.models.User;
import com.storage.models.VerificationToken;
import com.storage.models.requests.EmailRequest;
import com.storage.repositories.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailClient emailClient;
    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;

    public void sendQuoteConfirmation(String email) {
        User user = userService.findByEmail(email);
        sendQuoteConfirmation(user.getEmail(), user.getFirstName(), user.getLastName());
    }

    private void sendQuoteConfirmation(String email, String firstName, String lastName) {

        String subject = "Your Storage Space reservation";
        String body = """
                <body>
                <h1>Hi %s!</h1>
                <p>It means a lot to us that you have chosen Storage Space as your storage provider!</p>
                <p>We want you to know that we value your trust and will do everything we can to ensure</p>
                <p>you have a positive experience with us. Our team is committed to maintaining</p>
                <p>the highest standards and providing the best quality service to meet all your storage needs.</p>
                </body>
                """;

        EmailRequest emailRequest = new EmailRequest(
                email,
                String.format("%s %s ", firstName, lastName),
                subject,
                String.format(body, firstName)
        );

        emailClient.sendEmail(emailRequest);
    }

    public void sendEmailVerification(String email) {
        User user = userService.findByEmail(email);

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .userId(user.getId())
                .expiredTime(LocalDateTime.now().plusMinutes(10))
                .build();

        verificationTokenRepository.save(verificationToken);

        String subject = "Welcome to Storage Space!";

        String body = """
                <body>
                <h1>Hi there!</h1>
                <p>Thank you for signing up with Storage Space.</p>
                <p>You're one step away from completing the registration process. For security purposes, please click the link below to confirm your account:</p>
                <p><a href="%s">Click here to confirm</a></p>
                </body>
                """;

        EmailRequest emailRequest = new EmailRequest(
                email,
                String.format("%s %s ", user.getFirstName(), user.getLastName()),
                subject,
                String.format(body, "http://localhost:8080/api/v1/users/activate-email-account/" + token)
        );

        emailClient.sendEmail(emailRequest);
    }
}
