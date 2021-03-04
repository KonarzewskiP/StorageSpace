package com.storage.service;

import com.storage.exception.QuoteDetailsException;
import com.storage.model.Quote;
import com.storage.model.enums.DeliveryStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.storage.builders.MockDataForTest.createQuote;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@ExtendWith(SpringExtension.class)
public class QuoteServiceTest {

    @InjectMocks
    private QuoteService service;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    @DisplayName("should send email without triggering errors")
    void shouldSendEmailWithoutTriggeringErrors() {
        //given
        var quote = createQuote();
        //when
        var result = service.sendQuote(quote);
        //then
        assertThat(result.getStatus()).isEqualTo(DeliveryStatus.OK);
        assertThat(result.getEmail()).isEqualTo("johnBravo@gmail.com");
    }

    @Test
    @DisplayName("should throw QuoteDetailsException when quote is null")
    void shouldThrowQuoteDetailsExceptionWhenQuoteIsNull() {
        //given
        Quote quote = null;
        //when
        var result = catchThrowable(() -> service.sendQuote(quote));
        //then
        assertThat(result)
                .isInstanceOf(QuoteDetailsException.class)
                .hasMessageContaining("Invalid Quote!")
                .hasMessageContaining("Can not be null");
    }

    @Test
    @DisplayName("should throw QuoteDetailsException when quote is invalid")
    void shouldThrowQuoteDetailsExceptionWhenQuoteIsInvalid() {
        //given
        var quote = createQuote();
        quote.setEmail("");
        quote.setSurname("");
        //when
        var result = catchThrowable(() -> service.sendQuote(quote));
        //then
        assertThat(result)
                .isInstanceOf(QuoteDetailsException.class)
                .hasMessageContaining("Invalid Quote!")
                .hasMessageContaining("Email")
                .hasMessageContaining("Surname")
                .hasMessageContaining("Can not be empty");
    }

}
