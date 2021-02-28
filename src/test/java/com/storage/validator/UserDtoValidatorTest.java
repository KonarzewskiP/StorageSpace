package com.storage.validator;

import com.storage.model.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static com.storage.builders.MockDataForTest.createUserDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserDtoValidatorTest {

    private final UserDtoValidator validator = new UserDtoValidator();

    @Test
    @DisplayName("valid userDto should trigger no errors")
    void validUserDtoShouldTriggerNoErrors() {
        //given
        var userDto = createUserDto();
        //when
        var result = validator.validate(userDto);
        //then
        assertThat(result).hasSize(0);
    }

    @Test
    @DisplayName("should return error when userDto is null")
    void shouldReturnErrorWhenUserDtoIsNull() {
        //given
        UserDto userDto = null;
        //when
        Map<String, String> result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("UserDto"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when firstName is null")
    public void shouldReturnErrorWhenFirstNameIsNull() {
        //given
        var userDto = createUserDto();
        userDto.setFirstName(null);
        //when
        var result = validator.validate(userDto);
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
        var userDto = createUserDto();
        userDto.setLastName(null);
        //when
        var result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("LastName"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when gender is null")
    void shouldReturnErrorWhenGenderIsNull() {
        //given
        var userDto = createUserDto();
        userDto.setGender(null);
        //when
        var result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Gender"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when firstName is empty")
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        //given
        var userDto = createUserDto();
        userDto.setFirstName("");
        //when
        var result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("FirstName"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when first letter of firstName is lowercase")
    void shouldReturnErrorWhenFirstLetterOfFirstNameIsLowercase() {
        //given
        var userDto = createUserDto();
        userDto.setFirstName("antonio");
        //when
        var result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("FirstName"),
                () -> assertThat(result).containsValue("Should start from uppercase"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when lastName is empty")
    void shouldReturnErrorWhenLastNameIsEmpty() {
        //given
        var userDto = createUserDto();
        userDto.setLastName("");
        //when
        var result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("LastName"),
                () -> assertThat(result).containsValue("Can not be empty"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when first letter of lastName is lowercase")
    void shouldReturnErrorWhenFirstLetterOfLastNameIsLowercase() {
        //given
        var userDto = createUserDto();
        userDto.setLastName("snow");
        //when
        var result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("LastName"),
                () -> assertThat(result).containsValue("Should start from uppercase"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return two errors when firstName and LastName are invalid")
    void shouldReturnTwoErrorsWhenFirstNameAndLastNameAreInvalid() {
        //given
        var userDto = createUserDto();
        userDto.setFirstName("");
        userDto.setLastName("snow");
        //when
        var result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKeys("FirstName","LastName"),
                () -> assertThat(result).containsValues("Can not be empty","Should start from uppercase"),
                () -> assertThat(result).hasSize(2)
        );
    }
}


















