package com.storage.validator;

import com.storage.model.dto.UserDto;
import com.storage.validator.base.Validator;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class UserDtoValidator implements Validator<UserDto> {

    @Override
    public Map<String, String> validate(UserDto user) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(user)) {
            errors.put("UserDto", "Can not be null");
            return errors;
        }
        if (isNull(user.getFirstName())) {
            errors.put("FirstName", "Can not be null");
            return errors;
        }
        if (isNull(user.getLastName())) {
            errors.put("LastName", "Can not be null");
            return errors;
        }
        if (isNull(user.getGender())) {
            errors.put("Gender", "Can not be null");
            return errors;
        }


        if (!isFirstNameEmpty(user)) {
            errors.put("FirstName", "Can not be empty");
        } else if (!isFirstNameStartsFromUppercase(user)) {
            errors.put("FirstName", "Should start from uppercase");
        }

        if (!isLastNameEmpty(user)) {
            errors.put("LastName", "Can not be empty");
        } else if (!isLastNameStartsFromUppercase(user)) {
            errors.put("LastName", "Should start from uppercase");
        }
        return errors;
    }

    private boolean isFirstNameEmpty(UserDto userDto) {
        return !userDto.getFirstName().isBlank();
    }

    private boolean isLastNameEmpty(UserDto userDto) {
        return !userDto.getLastName().isBlank();
    }

    private boolean isFirstNameStartsFromUppercase(UserDto userDto) {
        return userDto.getFirstName().matches("([A-Z][a-z]+)");
    }

    private boolean isLastNameStartsFromUppercase(UserDto userDto) {
        return userDto.getLastName().matches("([A-Z][a-z]+)");
    }

}
