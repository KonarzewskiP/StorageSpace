package com.storage.validators;

import com.storage.service.postcodes_api.PostcodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.storage.builders.Fixtures.createAddressDto;
import static com.storage.builders.Fixtures.createPositivePostcodeValidationResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AddressDtoValidatorTest {

    @Mock
    private PostcodeService postcodeService;

    private AddressDtoValidator addressDtoValidator;

    @BeforeEach
    public void setUp() {
        addressDtoValidator = new AddressDtoValidator(postcodeService);
    }

    @Test
    @DisplayName("valid AddressDto should rigger no errors")
    void validAddressDtoShouldRiggerNoErrors() {
        //given
        var address = createAddressDto();
        var postcodeValidation = createPositivePostcodeValidationResponse();
        when(postcodeService.isValid(anyString())).thenReturn(postcodeValidation);
        //when
        var result = addressDtoValidator.validate(address);
        //then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(0);
    }


    @Test
    @DisplayName("should return error when city is null")
    void shouldReturnErrorWhenCityIsNull() {
        //given
        var address = createAddressDto();
        address.setCity(null);
        //when
        var result = addressDtoValidator.validate(address);
        //then
        assertAll(
                () -> assertThat(result).containsKey("City"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when street is null")
    void shouldReturnErrorWhenStreetIsNull() {
        //given
        var address = createAddressDto();
        address.setStreet(null);
        //when
        var result = addressDtoValidator.validate(address);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Street"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when postcode is null")
    void shouldReturnErrorWhenPostcodeIsNull() {
        //given
        var address = createAddressDto();
        address.setPostcode(null);
        //when
        var result = addressDtoValidator.validate(address);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Postcode"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when city is empty")
    void shouldReturnErrorWhenCityIsEmpty() {
        //given
        var postcodeValidation = createPositivePostcodeValidationResponse();
        when(postcodeService.isValid(anyString())).thenReturn(postcodeValidation);
        var address = createAddressDto();
        address.setCity("");
        //when
        var result = addressDtoValidator.validate(address);
        //then
        assertAll(
                () -> assertThat(result).containsKey("City"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when street is empty")
    void shouldReturnErrorWhenStreetIsEmpty() {
        //given
        var postcodeValidation = createPositivePostcodeValidationResponse();
        when(postcodeService.isValid(anyString())).thenReturn(postcodeValidation);
        var address = createAddressDto();
        address.setStreet("");
        //when
        var result = addressDtoValidator.validate(address);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Street"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when postcode is empty")
    void shouldReturnErrorWhenPostcodeIsEmpty() {
        //given
        var postcodeValidation = createPositivePostcodeValidationResponse();
        when(postcodeService.isValid(anyString())).thenReturn(postcodeValidation);
        var address = createAddressDto();
        address.setPostcode("");
        //when
        var result = addressDtoValidator.validate(address);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Postcode"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"AAAAAAA", "BBBBBB", "CCCCC", "12345678", "1234", "1", "12", "123", "E17 6I2",
            "KT6 7L9", "1EN1 1FT"})
    @DisplayName("should return error when postcode has invalid format")
    void shouldReturnErrorWhenPostcodeHasInvalidFormat(String postcode) {
        //given
        var address = createAddressDto();
        address.setPostcode(postcode);
        //when
        var result = addressDtoValidator.validate(address);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Postcode"),
                () -> assertThat(result).containsValue("Has incorrect format"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"SW12 0LT", "IG11 8BL", "SW11 3RX", "BR3 4AA", "E1W 2BX", "KT3 4PH", "EN1 1FT", "W5 1DN",
            "LU4 8DR","LS12 6HL", "B7 4LT", "OX4 2TZ"})
    @DisplayName("valid postcode should trigger no errors")
    void validPostcodeShouldTriggerNoErrors(String postcode) {
        //given
        var postcodeValidation = createPositivePostcodeValidationResponse();
        when(postcodeService.isValid(anyString())).thenReturn(postcodeValidation);
        var address = createAddressDto();
        address.setPostcode(postcode);
        //when
        var result = addressDtoValidator.validate(address);
        //then
        assertThat(result).hasSize(0);

    }

}
