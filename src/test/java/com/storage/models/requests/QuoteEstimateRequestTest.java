package com.storage.models.requests;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static com.storage.models.enums.StorageDuration.PLUS_1_YEAR;
import static com.storage.models.enums.StorageSize.LARGE_DOUBLE_GARAGE;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QuoteEstimateRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void itShouldPassWithoutError() {
        //Given
        String warehouseUuid = "warehouseUuid";
        LocalDate startDate = LocalDate.now();
        String firstName = "Tony";
        String lastName = "Hawk";
        String email = "tony@hawk.com";

        QuoteEstimateRequest request = new QuoteEstimateRequest(
                warehouseUuid,
                startDate,
                firstName,
                lastName,
                email,
                LARGE_DOUBLE_GARAGE,
                PLUS_1_YEAR,
                null);
        //When
        Set<ConstraintViolation<QuoteEstimateRequest>> violation = validator.validate(request);
        //Then
        assertThat(violation).isEmpty();
    }
}