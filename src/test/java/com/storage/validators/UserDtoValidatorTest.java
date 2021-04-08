package com.storage.validators;

import com.storage.models.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static com.storage.builders.Fixtures.createUserDto;
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
        assertThat(result).isNotNull();
        assertThat(result).hasSize(0);
    }

    // Check for null values
    @Test
    @DisplayName("should return error when userDto is null")
    void shouldReturnErrorWhenUserDtoIsNull() {
        //given
        UserDto userDto = null;
        //when
        var result = validator.validate(userDto);
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
    @DisplayName("should return error when email is null")
    void shouldReturnErrorWhenEmailIsNull() {
        //given
        var userDto = createUserDto();
        userDto.setEmail(null);
        //when
        var result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Email"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }

    @Test
    @DisplayName("should return error when role is null")
    void shouldReturnErrorWhenRoleIsNull() {
        //given
        var userDto = createUserDto();
        userDto.setRole(null);
        //when
        var result = validator.validate(userDto);
        //then
        assertAll(
                () -> assertThat(result).containsKey("Role"),
                () -> assertThat(result).containsValue("Can not be null"),
                () -> assertThat(result).hasSize(1)
        );
    }
    // tests for firstName
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
    // tests for lastName
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

    // tests for email
    @Test
    @DisplayName("should return error when email is empty")
    void shouldReturnErrorWhenEmailIsEmpty() {
        //given
        var userDto = createUserDto();
        userDto.setEmail("");
        //when
        var result = validator.validate(userDto);
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
        var userDto = createUserDto();
        userDto.setEmail(email);
        //when
        var result = validator.validate(userDto);
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
        var userDto = createUserDto();
        userDto.setEmail(email);
        //when
        var result = validator.validate(userDto);
        //then
        assertThat(result).hasSize(0);
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


















