package com.storage.validators;

import com.storage.exceptions.ObjectValidationException;
import com.storage.models.requests.CreateWarehouseRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class WarehouseDTOValidatorTest {
    private static final String NAME = "Orange";
    private static final String CITY = "London";
    private static final String STREET = "57 Walcot Square";

    @Nested
    class ValidateCreateWarehouseRequestTest {

        @ParameterizedTest
        @ValueSource(strings = {"AB1 0AA", "W1A 0AX", "M1 1AE", "B33 8TH", "EH6 6QQ", "CR2 6XH", "BN21TW", "S1P 1BL", "LS1 4LT", "NR3 1UB"})
        void itShouldPassRequestWithValidPostcode(String postcode) {
            //Given
            CreateWarehouseRequest request = new CreateWarehouseRequest(NAME, CITY, postcode, STREET, BigDecimal.TEN);
            //When + Then
            assertThatNoException().isThrownBy(() -> WarehouseDTOValidator.validate(request));
        }

        @ParameterizedTest
        @ValueSource(strings = {"G72      9RRF", "W@A 0AX", "LU7 2L1", "+CR2 6XH", "CR2 6X?H", "EC!A 1BBB", "DY1 4ATY", "CR92 1Q-", "LL23 7TFE", "PL10 1QD3"})
        void itShouldThrowErrorWhenInvalidPostcode(String postcode) {
            //Given
            CreateWarehouseRequest request = new CreateWarehouseRequest(NAME, CITY, postcode, STREET, BigDecimal.TEN);
            //When + Then
            assertThatThrownBy(() -> WarehouseDTOValidator.validate(request))
                    .isInstanceOf(ObjectValidationException.class)
                    .hasMessageContaining("Postcode")
                    .hasMessageContaining("Has incorrect format");
        }

        @Test
        void itShouldThrowErrorWithMessageForMultipleFields() {
            //Given
            CreateWarehouseRequest request = new CreateWarehouseRequest("", "", "+CR2 6XH", STREET, BigDecimal.TEN);
            //When + Then
            assertThatThrownBy(() -> WarehouseDTOValidator.validate(request))
                    .isInstanceOf(ObjectValidationException.class)
                    .hasMessageContaining("Postcode")
                    .hasMessageContaining("Has incorrect format")
                    .hasMessageContaining("Name")
                    .hasMessageContaining("City")
                    .hasMessageContaining("Can not be null or empty");
        }
    }

}