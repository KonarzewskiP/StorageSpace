package com.storage.validator;

import com.storage.model.dto.DirectorDto;
import com.storage.validator.base.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DirectorDtoValidator implements Validator<DirectorDto> {

    @Override
    public Map<String, String> validate(DirectorDto director) {
        Map<String, String> errors = new HashMap<>();
        if (!isFirstNameValid(director)) {
            errors.put("FirstName", "Can not be empty.");
        }
        if (!isLastNameValid(director)) {
            errors.put("LastName", "Can not be empty");
        }
        if (!isFirstNameStartsFromUppercase(director)) {
            errors.put("FirstName", "Should start from uppercase");
        }
        if (!isLastNameStartsFromUppercase(director)) {
            errors.put("LastName", "Should start from uppercase");
        }

        return errors;
    }

    private boolean isFirstNameValid(DirectorDto directorDto) {
        return directorDto.getFirstName() != null && !directorDto.getFirstName().isBlank();
    }

    private boolean isLastNameValid(DirectorDto directorDto) {
        return directorDto.getLastName() != null && !directorDto.getLastName().isBlank();
    }

    private boolean isFirstNameStartsFromUppercase(DirectorDto directorDto) {
        return directorDto.getFirstName().matches("([A-Z][a-z]+)");
    }

    private boolean isLastNameStartsFromUppercase(DirectorDto directorDto) {
        return directorDto.getLastName().matches("([A-Z][a-z]+)");
    }
}
