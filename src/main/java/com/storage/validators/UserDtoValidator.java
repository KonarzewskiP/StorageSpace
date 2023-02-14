package com.storage.validators;

import com.storage.models.requests.CreateUserRequest;
import com.storage.validators.base.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class UserDtoValidator implements Validator<CreateUserRequest> {

    @Override
    public Map<String, String> validate(CreateUserRequest user) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(user)) {
            errors.put("Create request", "Can not be null");
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
        }

        if (!isLastNameEmpty(user)) {
            errors.put("LastName", "Can not be empty");
        }

        if (!isEmailEmpty(user)) {
            errors.put("Email", "Can not be empty");
        } else if (!isEmailFormatValid(user.getEmail())) {
            errors.put("Email", "Has incorrect format");
        }
        return errors;
    }

    private boolean isFirstNameEmpty(CreateUserRequest createUserRequest) {
        return !createUserRequest.getFirstName().isBlank();
    }

    private boolean isLastNameEmpty(CreateUserRequest createUserRequest) {
        return !createUserRequest.getLastName().isBlank();
    }

    private boolean isEmailEmpty(CreateUserRequest createUserRequest) {
        return !createUserRequest.getEmail().isBlank();
    }

    private boolean isEmailFormatValid(String  email) {
        var pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(email);
        return matcher.find();
    }

}
