package com.storage.models.requests;

import com.storage.models.enums.Gender;
import com.storage.models.enums.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreateUserRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void itShouldCreateRequestWithoutTriggeringError() {
        //Given
        String firstName = "Tom";
        String lastName = "Ford";
        String email = "tom@ford.com";
        String password = "pswd";
        Role role = Role.ROLE_USER;
        Gender gender = Gender.MALE;

        CreateUserRequest request = new CreateUserRequest(firstName, lastName, email, password, role, gender);
        //When
        Set<ConstraintViolation<CreateUserRequest>> violation = validator.validate(request);
        //Then
        assertThat(violation).isEmpty();
    }
}