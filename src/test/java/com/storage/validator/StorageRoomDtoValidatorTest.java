package com.storage.validator;

import com.storage.model.dto.StorageRoomDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
