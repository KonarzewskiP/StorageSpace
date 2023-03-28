package com.storage.client;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.storage.exceptions.EmailException;
import com.storage.exceptions.SendGridException;
import com.storage.models.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class EmailClient {

    private final SendGrid sendGrid;

    @Value("${sengrid.email.from}")
    private String fromEmail;


    public EmailClient(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public void sendEmail(EmailRequest request) {
        log.info("Sending email with request: [{}]", request);
        Personalization personalization = new Personalization();

        Email recipient = new Email(request.getTo(), request.getRecipientName());
        personalization.addTo(recipient);
        Email from = new Email(fromEmail);

        Content content = new Content();
        content.setType("text/plain");
        content.setValue(request.getContent());

        Mail mail = new Mail();
        mail.setSubject(request.getSubject());
        mail.addPersonalization(personalization);
        mail.addContent(content);
        mail.setFrom(from);

        send(mail);
        log.info("Sent email with request: [{}]", request);
    }

    private void send(final Mail mail) {
        final Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");

        try {
            request.setBody(mail.build());
        } catch (IOException e) {
            throw new EmailException("Failed to build email body, mail " + mail, e);
        }

        try {
            Response response = sendGrid.api(request);

            if (response.getStatusCode() < 200 || response.getStatusCode() >= 300) {
                throw new SendGridException(String.format("SendGrid response error,status %d, body %s",
                        response.getStatusCode(), response.getBody()));
            }
        } catch (Exception e) {
            throw new SendGridException("Failed to send HTTP request to SendGrid, request: " + request, e);
        }
    }
}
