package com.storage.validators;

import com.storage.models.dto.UserDto;
import com.storage.validators.base.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
        if (isNull(user.getEmail())) {
            errors.put("Email", "Can not be null");
            return errors;
        }
        if (isNull(user.getRole())) {
            errors.put("Role", "Can not be null");
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

        if (!isEmailEmpty(user)) {
            errors.put("Email", "Can not be empty");
        } else if (!isEmailFormatValid(user)) {
            errors.put("Email", "Has incorrect format");
        }
        return errors;
    }
    private boolean isFirstNameEmpty(UserDto userDto) {
        return !userDto.getFirstName().isBlank();
    }

    private boolean isLastNameEmpty(UserDto userDto) {
        return !userDto.getLastName().isBlank();
    }

    private boolean isEmailEmpty(UserDto userDto) {
        return !userDto.getEmail().isBlank();
    }

    private boolean isFirstNameStartsFromUppercase(UserDto userDto) {
        return userDto.getFirstName().matches("([A-Z][a-z]+)");
    }
    private boolean isLastNameStartsFromUppercase(UserDto userDto) {
        return userDto.getLastName().matches("([A-Z][a-z]+)");
    }
    private boolean isEmailFormatValid(UserDto userDto) {
        var pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(userDto.getEmail());
        return matcher.find();
    }

}
