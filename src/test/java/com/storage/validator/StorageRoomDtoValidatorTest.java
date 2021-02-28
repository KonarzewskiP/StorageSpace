package com.storage.validator;

import com.storage.model.dto.StorageRoomDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.storage.builders.MockDataForTest.START_DATE;
import static com.storage.builders.MockDataForTest.createStorageRoomDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class StorageRoomDtoValidatorTest {

    private final StorageRoomDtoValidator validator = new StorageRoomDtoValidator();

    @Test
    @DisplayName("valid storageRoomDto should trigger no errors")
    void validStorageRoomDtoShouldTriggerNoErrors() {
        //given
        var storageRoomDto = createStorageRoomDto();
        //when
        var result = validator.validate(storageRoomDto);
        //then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(0);
    }

    @Test
    @DisplayName("should return error when storageRoomDto is null")
    void shouldReturnErrorWhenStorageRoomDtoIsNull() {
        //given
        StorageRoomDto storageRoomDto = null;
        //when
        var result = validator.validate(storageRoomDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("StorageRoomDto"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when size is null")
    void shouldReturnErrorWhenSizeIsNull() {
        //given
        var storageRoomDto = createStorageRoomDto();
        storageRoomDto.setSize(null);
        //when
        var result = validator.validate(storageRoomDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Size"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when reserved is null")
    void shouldReturnErrorWhenReservedIsNull() {
        //given
        var storageRoomDto = createStorageRoomDto();
        storageRoomDto.setReserved(null);
        //when
        var result = validator.validate(storageRoomDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Reserved"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when storage is reserved and startDate is null")
    void shouldReturnErrorWhenStorageIsReservedAndStartDateIsNull() {
        //given
        var storageRoomDto = createStorageRoomDto();
        storageRoomDto.setReserved(true);
        storageRoomDto.setStartDate(null);

        //when
        var result = validator.validate(storageRoomDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("StartDate"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when storage is reserved and endDate is null")
    void shouldReturnErrorWhenStorageIsReservedAndEndDateIsNull() {
        //given
        var storageRoomDto = createStorageRoomDto();
        storageRoomDto.setReserved(true);
        storageRoomDto.setStartDate(START_DATE);
        storageRoomDto.setEndDate(null);

        //when
        var result = validator.validate(storageRoomDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("EndDate"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when startDate and endDate is the same")
    void shouldReturnErrorWhenStartDateAndEndDateIsTheSame() {
        //given
        var storageRoomDto = createStorageRoomDto();
        storageRoomDto.setReserved(true);
        storageRoomDto.setStartDate(START_DATE);
        storageRoomDto.setEndDate(START_DATE);

        //when
        var result = validator.validate(storageRoomDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Invalid Date"),
                () -> assertThat(result).containsValue("Start and end date is the same"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when startDate is after endDate")
    void shouldReturnErrorWhenStartDateIsAfterEndDate() {
        //given
        var storageRoomDto = createStorageRoomDto();
        storageRoomDto.setReserved(true);
        storageRoomDto.setStartDate(START_DATE.plusSeconds(10));
        storageRoomDto.setEndDate(START_DATE);

        //when
        var result = validator.validate(storageRoomDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Invalid Date"),
                () -> assertThat(result).containsValue("Start date must be before end date"),
                () -> assertThat(result).hasSize(1)
        );
    }

}
