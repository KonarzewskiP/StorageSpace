package com.storage.validator;

import com.storage.model.dto.DirectorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static com.storage.builders.MockDataForTest.createDirectorDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class DirectorDtoValidatorTest {

    private final DirectorDtoValidator validator = new DirectorDtoValidator();

    @Test
    @DisplayName("valid directorDto should trigger no errors")
    void validDirectorDtoShouldTriggerNoErrors() {
        //given
        var directorDto = createDirectorDto();
        //when
        var result = validator.validate(directorDto);
        //then
        assertThat(result).hasSize(0);
    }

    @Test
    @DisplayName("should return error when directorDto is null")
    void shouldReturnErrorWhenDirectorDtoIsNull() {
        //given
        DirectorDto directorDto = null;
        //when
        Map<String, String> result = validator.validate(directorDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("DirectorDto"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when firstName is null")
    public void shouldReturnErrorWhenFirstNameIsNull() {
        //given
        var directorDto = createDirectorDto();
        directorDto.setFirstName(null);
        //when
        var result = validator.validate(directorDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("FirstName"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when lastName is null")
    void shouldReturnErrorWhenLastNameIsNull() {
        //given
        var directorDto = createDirectorDto();
        directorDto.setLastName(null);
        //when
        var result = validator.validate(directorDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("LastName"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return Error when firstName is empty")
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        //given
        var directorDto = createDirectorDto();
        directorDto.setFirstName("");
        //when
        var result = validator.validate(directorDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("FirstName"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return Error when first letter of firstName is lowercase")
    void shouldReturnErrorWhenFirstLetterOfFirstNameIsLowercase() {
        //given
        var directorDto = createDirectorDto();
        directorDto.setFirstName("antonio");
        //when
        var result = validator.validate(directorDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("FirstName"),
                () -> assertThat(result).containsValue("Should start from uppercase"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return Error when lastName is empty")
    void shouldReturnErrorWhenLastNameIsEmpty() {
        //given
        var directorDto = createDirectorDto();
        directorDto.setLastName("");
        //when
        var result = validator.validate(directorDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("LastName"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return Error when first letter of lastName is lowercase")
    void shouldReturnErrorWhenFirstLetterOfLastNameIsLowercase() {
        //given
        var directorDto = createDirectorDto();
        directorDto.setLastName("snow");
        //when
        var result = validator.validate(directorDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("LastName"),
                () -> assertThat(result).containsValue("Should start from uppercase"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return two Errors when firstName and LastName are invalid")
    void shouldReturnTwoErrorsWhenFirstNameAndLastNameAreInvalid() {
        //given
        var directorDto = createDirectorDto();
        directorDto.setFirstName("");
        directorDto.setLastName("snow");
        //when
        var result = validator.validate(directorDto);
        //then
        assertAll(
                () -> assertThat(result).containsKeys("FirstName","LastName"),
                () -> assertThat(result).containsValues("Can not be empty","Should start from uppercase"),
                () -> assertThat(result).hasSize(2)
        );
    }
}


















