package com.storage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.repositories.WarehouseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static com.storage.builders.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PostcodeServiceTest {

    @InjectMocks
    private PostcodeService service;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private WarehouseRepository warehouseRepository;


    @Test
    @DisplayName("should return coordinates of postcode")
    void shouldReturnCoordinatesOfPostcode() {
        //given
        var postcode = "SW178EF";
        var postcodeResponse = createPostcodeResponse();
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(postcodeResponse));
        //when
        var result = service.getSingleCoordinates(postcode);
        //then
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(200),
                () -> assertThat(result.getPostcodeResultDTO().getLongitude()).isEqualTo(-0.155261),
                () -> assertThat(result.getPostcodeResultDTO().getLatitude()).isEqualTo(51.431047),
                () -> assertThat(result.getPostcodeResultDTO().getPostcode()).isEqualTo(postcode)
        );
    }

    @Test
    @DisplayName("should return object with valid result for postcode verification")
    void shouldReturnObjectWithValidResultForPostcodeVerification() {
        //given
        var postcode = "SW178EF";
        var postcodeValidation = createPositivePostcodeValidationResponse();
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(postcodeValidation));
        //when
        var result = service.isValid(postcode);
        //then
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(200),
                () -> assertThat(result.getResult()).isTrue()
        );
    }

    @Test
    @DisplayName("should return object with false result for invalid postcode verification")
    void shouldReturnObjectWithFalseResultForInvalidPostcodeVerification() {
        //given
        var postcode = "SW17887";
        var postcodeValidation = createNegativePostcodeValidationResponse();
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(postcodeValidation));
        //when
        var result = service.isValid(postcode);
        //then
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(200),
                () -> assertThat(result.getResult()).isFalse()
        );
    }
}
