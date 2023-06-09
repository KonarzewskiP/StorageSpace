package com.storage.client;

import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.storage.exceptions.SendGridException;
import com.storage.models.requests.EmailRequest;
import org.apache.http.ConnectionClosedException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class EmailClientTest {

    @InjectMocks
    private EmailClient underTest;
    @Mock
    private SendGrid sendGrid;

    private final String RECIPIENT_EMAIL = "dear@customer.com";
    private final String RECIPIENT_NAME = "dear@customer.com";
    private final String SUBJECT = "dear@customer.com";
    private final String CONTENT = "dear@customer.com";

    @Nested
    class SendEmailTest {


        @Test
        void itShouldThrowErrorWhenResponseStatusCodeFromSendGridIsOutsideRangeOf200To299() throws IOException {
            //Given
            EmailRequest emailRequest = new EmailRequest(RECIPIENT_EMAIL, RECIPIENT_NAME, SUBJECT, CONTENT);

            //... received response with status code 400 from the SendGrid client
            Response response = new Response(400, "BadRequestException", null);
            given(sendGrid.api(any())).willReturn(response);
            //When + Then
            assertThatThrownBy(() -> underTest.sendEmail(emailRequest))
                    .isInstanceOf(SendGridException.class)
                    .hasMessageContaining(String.format("SendGrid response error,status %d, body %s",
                            response.getStatusCode(), response.getBody()));
        }

        @Test
        void itShouldThrowExceptionWhenSendGridClientThrowError() throws IOException {
            //Given
            EmailRequest emailRequest = new EmailRequest(RECIPIENT_EMAIL, RECIPIENT_NAME, SUBJECT, CONTENT);
            //... SendGrid client throw error
            given(sendGrid.api(any())).willThrow(new ConnectionClosedException());
            //When + Then
            assertThatThrownBy(() -> underTest.sendEmail(emailRequest))
                    .isInstanceOf(SendGridException.class)
                    .hasMessageContaining(String.format(
                            "Failed to send HTTP request to SendGrid, to: [%s], subject: [%s]", RECIPIENT_EMAIL, SUBJECT));
        }
    }

}

