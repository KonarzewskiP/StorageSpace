package com.storage.validators;

import com.storage.models.dto.WarehouseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.storage.builders.MockDataForTest.createWarehouseDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class WarehouseDtoValidatorTest {

    private final WarehouseDtoValidator validator = new WarehouseDtoValidator();

    @Test
    @DisplayName("valid WarehouseDto should trigger no Errors")
    void validWarehouseDtoShouldTriggerNoErrors() {
        //given
        var warehouse = createWarehouseDto();
        //when
        var result = validator.validate(warehouse);
        //then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(0);
    }

    @Test
    @DisplayName("should return error when WarehouseDto is null")
    void shouldReturnErrorWhenWarehouseDtoIsNull() {
        //given
        WarehouseDto warehouseDto = null;
        //when
        var result = validator.validate(warehouseDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("WarehouseDto"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when name is null")
    void shouldReturnErrorWhenNameIsNull() {
        //given
        var warehouseDto = createWarehouseDto();
        warehouseDto.setName(null);
        //when
        var result = validator.validate(warehouseDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Name"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when city is null")
    void shouldReturnErrorWhenCityIsNull() {
        //given
        var warehouseDto = createWarehouseDto();
        warehouseDto.setCity(null);
        //when
        var result = validator.validate(warehouseDto);
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
        var warehouseDto = createWarehouseDto();
        warehouseDto.setStreet(null);
        //when
        var result = validator.validate(warehouseDto);
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
        var warehouseDto = createWarehouseDto();
        warehouseDto.setPostCode(null);
        //when
        var result = validator.validate(warehouseDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Postcode"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when name is empty")
    void shouldReturnErrorWhenNameIsEmpty() {
        //given
        var warehouseDto = createWarehouseDto();
        warehouseDto.setName("");
        //when
        var result = validator.validate(warehouseDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Name"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when city is empty")
    void shouldReturnErrorWhenCityIsEmpty() {
        //given
        var warehouseDto = createWarehouseDto();
        warehouseDto.setCity("");
        //when
        var result = validator.validate(warehouseDto);
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
        var warehouseDto = createWarehouseDto();
        warehouseDto.setStreet("");
        //when
        var result = validator.validate(warehouseDto);
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
        var warehouseDto = createWarehouseDto();
        warehouseDto.setPostCode("");
        //when
        var result = validator.validate(warehouseDto);
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
        var warehouseDto = createWarehouseDto();
        warehouseDto.setPostCode(postcode);
        //when
        var result = validator.validate(warehouseDto);
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
        var warehouseDto = createWarehouseDto();
        warehouseDto.setPostCode(postcode);
        //when
        var result = validator.validate(warehouseDto);
        //then
        assertThat(result).hasSize(0);

    }

}