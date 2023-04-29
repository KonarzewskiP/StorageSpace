package com.storage.validators;

import com.storage.exceptions.ObjectValidationException;
import com.storage.models.requests.CreateUserRequest;
import com.storage.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.storage.utils.StringUtils.isEmailFormatValid;
import static java.util.Objects.isNull;

public class UserDtoValidator {
    public static void validate(CreateUserRequest user) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(user))
            throw new ObjectValidationException("CreateUserRequest can not be null");

        if (StringUtils.isBlank(user.firstName()))
            errors.put("FirstName", "Can not be null or empty");
        if (StringUtils.isBlank(user.lastName()))
            errors.put("LastName", "Can not be null or empty");
        if (StringUtils.isBlank(user.email()))
            errors.put("Email", "Can not be null or empty");
        if (isNull(user.role()))
            errors.put("Role", "Can not be null");
        if (isNull(user.gender()))
            errors.put("Gender", "Can not be null");

        if (!isEmailFormatValid(user.email())) {
            errors.put("Email", "Has incorrect format");
        }

        if (!errors.isEmpty()) {
            throw new ObjectValidationException("Invalid UserDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " -> " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
    }



}
