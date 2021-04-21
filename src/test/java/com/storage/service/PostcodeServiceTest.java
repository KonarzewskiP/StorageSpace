package com.storage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.Address;
import com.storage.models.Warehouse;
import com.storage.models.dto.externals.postcode.PostcodeResponse;
import com.storage.models.dto.externals.postcode.PostcodeResponseMany;
import com.storage.models.dto.externals.postcode.Result;
import com.storage.models.dto.externals.postcode.ResultMany;
import com.storage.repositories.WarehouseRepository;
import com.storage.service.postcodes_api.PostcodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
        var result = service.getCoordinatesPostcode(postcode);
        //then
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(200),
                () -> assertThat(result.getResult().getLongitude()).isEqualTo(-0.155261),
                () -> assertThat(result.getResult().getLatitude()).isEqualTo(51.431047),
                () -> assertThat(result.getResult().getPostcode()).isEqualTo(postcode)
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

    @Test
    @DisplayName("should return object response with List of coordinates for each postcode")
    void shouldReturnObjectResponseWithListOfCoordinatesForEachPostcode() {
        //given
        var postcodesResponse = getPostcodeResponseForManyPostcodes();
        ReflectionTestUtils.setField(service, "postcodeUrl", "postcode.api.url");
        when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(postcodesResponse);
        //when
        var result = service.getCoordinatesPostcodes(List.of());
        //then
        assertAll(
                () -> assertThat(result.getResult().size()).isEqualTo(4),
                () -> assertThat(result.getResult().get(0).getResult().getPostcode()).isEqualTo("N183AF")
        );

    }

    @Test
    @DisplayName("should return ordered list of warehouses by distance from postcode")
    void shouldReturnOrderedListOfWarehousesByDistanceFromPostcode() {
        //given
        var postcode = "e176pj";
        var listOfWarehouses = getListOfWarehouses();
        var postcodeCoordinates = PostcodeResponse.builder()
                .status(200)
                .result((Result.builder()
                        .postcode("E176PJ")
                        .longitude(-0.027387)
                        .latitude(51.585106)
                        .build()))
                .build();
        var postcodesResponse = getPostcodeResponseForManyPostcodes();
        ReflectionTestUtils.setField(service, "postcodeUrl", "postcode.api.url");
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(postcodeCoordinates));
        when(warehouseRepository.findAll()).thenReturn(listOfWarehouses);
        when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(postcodesResponse);
        //when
        var result = service.getOrderedWarehousesByDistanceFromPostcode(postcode);
        //then
        assertAll(
                () -> assertThat(result.get(0).getId()).isEqualTo(13),
                () -> assertThat(result.get(0).getName()).isEqualTo("Edmonton"),
                () -> assertThat(result.get(1).getId()).isEqualTo(6),
                () -> assertThat(result.get(1).getName()).isEqualTo("Barking"),
                () -> assertThat(result.get(2).getId()).isEqualTo(24),
                () -> assertThat(result.get(2).getName()).isEqualTo("Fulham"),
                () -> assertThat(result.get(3).getId()).isEqualTo(2),
                () -> assertThat(result.get(3).getName()).isEqualTo("Bromley")
        );

    }


    private List<Warehouse> getListOfWarehouses() {
        return List.of(Warehouse.builder()
                        .id(13L)
                        .name("Edmonton")
                        .address(Address.builder()
                                .postcode("N183AF")
                                .build())
                        .build(),
                Warehouse.builder()
                        .id(24L)
                        .name("Fulham")
                        .address(Address.builder()
                                .postcode("SW62ST")
                                .build())
                        .build(),
                Warehouse.builder()
                        .id(2L)
                        .name("Bromley")
                        .address(Address.builder()
                                .postcode("BR13RB")
                                .build())
                        .build(),
                Warehouse.builder()
                        .id(6L)
                        .name("Barking")
                        .address(Address.builder()
                                .postcode("IG118BL")
                                .build())
                        .build());
    }

    private PostcodeResponseMany getPostcodeResponseForManyPostcodes() {
        return PostcodeResponseMany.builder()
                .status(200)
                .result(List.of(ResultMany.builder()
                                .result(Result.builder()
                                        .postcode("N183AF")
                                        .longitude(-0.045596)
                                        .latitude(51.612134)
                                        .build())
                                .build(),
                        ResultMany.builder()
                                .result(Result.builder()
                                        .postcode("SW62ST")
                                        .longitude(-0.185081)
                                        .latitude(51.468849)
                                        .build())
                                .build(),
                        ResultMany.builder()
                                .result(Result.builder()
                                        .postcode("BR13RB")
                                        .longitude(0.011502)
                                        .latitude(51.410732)
                                        .build())
                                .build(),
                        ResultMany.builder()
                                .result(Result.builder()
                                        .postcode("IG118BL")
                                        .longitude(0.068501)
                                        .latitude(51.539173)
                                        .build())
                                .build()))
                .build();
    }
}
