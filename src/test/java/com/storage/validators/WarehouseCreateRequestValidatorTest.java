package com.storage.validators;

import com.storage.models.dto.WarehouseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.storage.builders.Fixtures.createWarehouseDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class WarehouseCreateRequestValidatorTest {

    private WarehouseCreateRequestValidator warehouseValidator;

    @BeforeEach
    public void setUp(){
        warehouseValidator = new WarehouseCreateRequestValidator();
    }

    @Test
    @DisplayName("valid WarehouseDto should trigger no Errors")
    void validWarehouseDtoShouldTriggerNoErrors() {
        //given
        var warehouse = createWarehouseDto();
        //when
        var result = warehouseValidator.validate(warehouse);
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
        var result = warehouseValidator.validate(warehouseDto);
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
        var result = warehouseValidator.validate(warehouseDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Name"),
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
        var result = warehouseValidator.validate(warehouseDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Name"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }






}