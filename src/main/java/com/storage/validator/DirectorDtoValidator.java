package com.storage.validator;

import com.storage.model.dto.DirectorDto;
import com.storage.validator.base.Validator;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class DirectorDtoValidator implements Validator<DirectorDto> {

    @Override
    public Map<String, String> validate(DirectorDto director) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(director)) {
            errors.put("DirectorDto", "Can not be null");
            return errors;
        }
        if (isNull(director.getFirstName())) {
            errors.put("FirstName", "Can not be null");
            return errors;
        }
        if (isNull(director.getLastName())) {
            errors.put("LastName", "Can not be null");
            return errors;
        }
        if (isNull(director.getGender())) {
            errors.put("Gender", "Can not be null");
            return errors;
        }


        if (!isFirstNameEmpty(director)) {
            errors.put("FirstName", "Can not be empty");
        } else if (!isFirstNameStartsFromUppercase(director)) {
            errors.put("FirstName", "Should start from uppercase");
        }

        if (!isLastNameEmpty(director)) {
            errors.put("LastName", "Can not be empty");
        } else if (!isLastNameStartsFromUppercase(director)) {
            errors.put("LastName", "Should start from uppercase");
        }

        return errors;
    }

    private boolean isFirstNameEmpty(DirectorDto directorDto) {
        return !directorDto.getFirstName().isBlank();
    }

    private boolean isLastNameEmpty(DirectorDto directorDto) {
        return !directorDto.getLastName().isBlank();
    }

    private boolean isFirstNameStartsFromUppercase(DirectorDto directorDto) {
        return directorDto.getFirstName().matches("([A-Z][a-z]+)");
    }

    private boolean isLastNameStartsFromUppercase(DirectorDto directorDto) {
        return directorDto.getLastName().matches("([A-Z][a-z]+)");
    }

}
