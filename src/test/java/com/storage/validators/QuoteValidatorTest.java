package com.storage.validators;

import com.storage.models.Quote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static com.storage.builders.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;

public class QuoteValidatorTest {

    private final QuoteValidator validator = new QuoteValidator();

    @Test
    @DisplayName("valid quote should trigger no errors")
    void validQuoteShouldTriggerNoErrors() {
        //given
        var quote = createQuote();
        //when
        var result = validator.validate(quote);
        //then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(0);
    }

    @Test
    @DisplayName("should return error when quote is null")
    void shouldReturnErrorWhenQuoteIsNull() {
        //given
        Quote quote = null;
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Quotation"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when firstName is null")
    void shouldReturnErrorWhenFirstNmeIsNull() {
        //given
        var quote = createQuote();
        quote.setFirstName(null);
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("FirstNme"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when warehouse is null")
    void shouldReturnErrorWhenWarehouseIsNull() {
        //given
        var quote = createQuote();
        quote.setWarehouseName(null);
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Warehouse"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when surname is null")
    void shouldReturnErrorWhenSurnameIsNull() {
        //given
        var quote = createQuote();
        quote.setSurname(null);
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Surname"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when email is null")
    void shouldReturnErrorWhenEmailIsNull() {
        //given
        var quote = createQuote();
        quote.setEmail(null);
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Email"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when size is null")
    void shouldReturnErrorWhenSizeIsNull() {
        //given
        var quote = createQuote();
        quote.setSize(null);
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Size"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when typeOfAccount is null")
    void shouldReturnErrorWhenTypeOfAccountIsNull() {
        //given
        var quote = createQuote();
        quote.setType(null);
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("TypeOfAccount"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when startDate is null")
    void shouldReturnErrorWhenStartDateIsNull() {
        //given
        var quote = createQuote();
        quote.setStartDate(null);
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("StartDate"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when duration is null")
    void shouldReturnErrorWhenDurationIsNull() {
        //given
        var quote = createQuote();
        quote.setDuration(null);
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Duration"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when startDate is before present")
    void shouldReturnErrorWhenStartDateIsBeforePresent() {
        //given
        var quote = createQuote();
        quote.setStartDate(LocalDate.now().minusDays(1));
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("StartDate"),
                () -> assertThat(result).containsValue("Must be after today"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when startDate is the same day as present")
    void shouldReturnErrorWhenStartDateIsTheSameDayAsPresent() {
        //given
        var quote = createQuote();
        quote.setStartDate(LocalDate.now());
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("StartDate"),
                () -> assertThat(result).containsValue("Must be after today"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when firstName is empty")
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        //given
        var quote = createQuote();
        quote.setFirstName("");
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("FirstNme"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when surname is empty")
    void shouldReturnErrorWhenSurnameIsEmpty() {
        //given
        var quote = createQuote();
        quote.setSurname("");
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Surname"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when warehouse is empty")
    void shouldReturnErrorWhenWarehouseIsEmpty() {
        //given
        var quote = createQuote();
        quote.setWarehouseName("");
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Warehouse"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }
    @Test
    @DisplayName("should return error when email is empty")
    void shouldReturnErrorWhenEmailIsEmpty() {
        //given
        var quote = createQuote();
        quote.setEmail("");
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Email"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"fakeemail.com","@co.com","fake@com","a@£a.com","a!d4a*m@hotmail.com"})
    @DisplayName("should return error when email has invalid format")
    void shouldReturnErrorWhenEmailHasInvalidFormat(String email) {
        //given
        var quote = createQuote();
        quote.setEmail(email);
        //when
        var result = validator.validate(quote);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Email"),
                () -> assertThat(result).containsValue("Has incorrect format"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"email@valid.com","BigStorrage123@yellow.co.uk","address@spaceX.com","martin.book@hotmail.com","proxima2Centauri@hotmail.com"})
    @DisplayName("valid email should trigger no errors")
    void validEmailShouldTriggerNoErrors(String email) {
        //given
        var quote = createQuote();
        quote.setEmail(email);
        //when
        var result = validator.validate(quote);
        //then
        assertThat(result).hasSize(0);
    }
}
