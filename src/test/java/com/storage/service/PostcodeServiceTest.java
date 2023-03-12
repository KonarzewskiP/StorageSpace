package com.storage.service;

import com.storage.client.PostcodeClient;
import com.storage.exceptions.BadRequestException;
import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeDetails;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PostcodeServiceTest {

    @InjectMocks
    private PostcodeService underTest;
    @Mock
    PostcodeClient postcodeClient;

    @Nested
    class IsValidTest {

        @ParameterizedTest
        @ValueSource(strings = {"Z1A 0B1", "A1A 0B11", "s1", "14231", "6UUSW9", "1ECA 1BB", "EC11 1B", "EC11", "EC1A BB1", "ECA BB"})
        void itShouldThrowErrorIfPostcodeHasInvalidFormat(String invalidPostcode) {
            //Given
            //When
            //Then
            assertThatThrownBy(() -> underTest.isValid(invalidPostcode))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining(String.format("Postcode format is invalid! Invalid postcode: [%s]. Only UK postcodes can be validate!", invalidPostcode));
        }

        @ParameterizedTest
        @ValueSource(strings = {"SW1W 0NY", "PO16 7GZ", "GU167HF", "L1 8JQ", "L1      8JQ", " L 1 8 J Q "})
        void itShouldPassPostcodeValidationAndReturnDTO(String postcode) {
            //Given
            // ... service successfully return valid postcode
            PostcodeValidateDTO postcodeValidateDTO = PostcodeValidateDTO.builder()
                    .result(true)
                    .build();
            given(postcodeClient.isValid(anyString())).willReturn(postcodeValidateDTO);
            //When
            PostcodeValidateDTO result = underTest.isValid(postcode);
            //Then
            assertThat(result).isNotNull();
            assertThat(result).usingRecursiveComparison().isEqualTo(postcodeValidateDTO);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void itShouldThrowErrorIfPostcodeIsNullOrEmpty(String invalidPostcode) {
            //Given
            //When
            //Then
            assertThatThrownBy(() -> underTest.isValid(invalidPostcode))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining("Postcode can not be empty or null");
        }
    }

    @Nested
    class GetDetailsTest {

        @ParameterizedTest
        @ValueSource(strings = {"SW1W 0NY", "PO16 7GZ", "GU167HF", "L1 8JQ", "L1      8JQ", " L 1 8 J Q "})
        void itShouldPassPostcodeValidationAndReturnDTO(String postcode) {
            //Given
            float lat = 51.4627f;
            float lng = -0.169f;
            // ... service successfully return postcode details
            PostcodeDTO postcodeDTO = PostcodeDTO.builder()
                    .status(200)
                    .result(PostcodeDetails.builder()
                            .postcode(postcode)
                            .lat(lat)
                            .lng(lng)
                            .build())
                    .build();
            given(postcodeClient.getDetails(anyString())).willReturn(postcodeDTO);
            //When
            PostcodeDTO result = underTest.getDetails(postcode);
            //Then
            assertThat(result).isNotNull();
            assertThat(result).usingRecursiveComparison().isEqualTo(postcodeDTO);
        }
    }
}
