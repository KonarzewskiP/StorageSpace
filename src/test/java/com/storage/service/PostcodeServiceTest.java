package com.storage.service;

import com.google.gson.GsonBuilder;
import com.storage.exceptions.ResourceNotFoundException;
import com.storage.models.Warehouse;
import com.storage.repositories.WarehouseRepository;
import com.storage.service.postcodes_api.PostcodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.storage.builders.MockDataForTest.createMapForBulkPostcodesRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PostcodeServiceTest {

    private static final String POSTCODE_BASE_CALL = "https://api.postcodes.io/postcodes";

    @InjectMocks
    private PostcodeService service;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Test
    @DisplayName("should return true for valid postcode")
    void shouldReturnTrueForValidPostcode() {
        //given
        var postcode = "NW13LH";
        //when
        var result = service.isValid(postcode);
        //then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"IP83NL", "IP83NL/validate"})
    @DisplayName("should return 200 when sending correct single request postcode")
    void shouldReturn200WhenSendingCorrectSingleRequestPostcode(String variable) throws Exception {
        //given
        var client = HttpClient.newBuilder().build();
        //when
        var request = HttpRequest
                .newBuilder(URI.create(POSTCODE_BASE_CALL + "/" + variable))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException for invalid postcode")
    void shouldThrowResourceNotFoundExceptionForInvalidPostcode() {
        //given
        var postcode = "NW1311";
        //when
        var throwable = catchThrowable(() -> service.isValid(postcode));
        //then
        assertAll(
                () -> assertThat(throwable).isInstanceOf(ResourceNotFoundException.class),
                () -> assertThat(throwable).hasMessageContaining("Postcode not found with Postcode:")
        );
    }

    @Test
    @DisplayName("should return 404 when sending bad request for postcode validation")
    void shouldReturn404WhenSendingBadRequestForPostcodeValidation() throws Exception {
        //given
        var client = HttpClient.newBuilder().build();
        //when
        var request = HttpRequest
                .newBuilder(URI.create(POSTCODE_BASE_CALL + "/NW13L"))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //then
        assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("should return 200 when sending correct bulk request for postcodes")
    void shouldReturn200WhenSendingCorrectBulkRequestForPostcodes() throws Exception {
        //given
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var client = HttpClient.newBuilder().build();
        //when
        var request = HttpRequest
                .newBuilder(URI.create(POSTCODE_BASE_CALL))
                .version(HttpClient.Version.HTTP_2)
                .header("content-type", "application/json;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(createMapForBulkPostcodesRequest())))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("should return 400 when sending bad bulk request for postcodes")
    void shouldReturn400WhenSendingBadBulkRequestForPostcodes() throws Exception {
        //given
        var client = HttpClient.newBuilder().build();
        //when
        var request = HttpRequest
                .newBuilder(URI.create(POSTCODE_BASE_CALL))
                .version(HttpClient.Version.HTTP_2)
                .header("content-type", "application/json;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //then
        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @DisplayName("should return object with coordinates for single postcode")
    void shouldReturnObjectWithCoordinatesForSinglePostcode() {
        //given
        var postcode = "NW13LH";
        //when
        var result = service.getLatAndLngForSinglePostcode(postcode);
        //then
        assertAll(
                () -> assertThat(result.getPostcode()).isEqualTo("NW1 3LH"),
                () -> assertThat(result.getLatitude()).isEqualTo(51.527516),
                () -> assertThat(result.getLongitude()).isEqualTo(-0.143089),
                () -> assertThat(result.getError()).isNull()
        );
    }

    @Test
    @DisplayName("should return object with error field for invalid postcode")
    void shouldReturnObjectWithErrorFieldForInvalidPostcode() {
        //given
        var postcode = "NW1333";
        //when
        var result = service.getLatAndLngForSinglePostcode(postcode);
        //then
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(404),
                () -> assertThat(result.getPostcode()).isNull(),
                () -> assertThat(result.getLatitude()).isEqualTo(0.0),
                () -> assertThat(result.getLongitude()).isEqualTo(0.0),
                () -> assertThat(result.getError()).contains("Invalid postcode")
        );
    }

    @Test
    @DisplayName("should return object with list of coordinates for many postcodes")
    void shouldReturnObjectWithListOfCoordinatesForManyPostcodes() {
        //given
        var listOfPostcodes = List.of("CM16 4BL", "CF399BQ", "ca9 3tw", "xxxxxxx");
        //when
        var result = service.getLatAndLngForManyPostcodes(listOfPostcodes);
        var postcodes = result.getResult();
        //then
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(200),
                () -> assertThat(postcodes.size()).isEqualTo(4),
                () -> assertThat(postcodes.get(0).getPostcode()).isEqualTo("CM16 4BL"),
                () -> assertThat(postcodes.get(0).getLatitude()).isEqualTo(51.699288),
                () -> assertThat(postcodes.get(0).getLongitude()).isEqualTo(0.111132),
                () -> assertThat(postcodes.get(1).getPostcode()).isEqualTo("CF39 9BQ"),
                () -> assertThat(postcodes.get(1).getLatitude()).isEqualTo(51.614229),
                () -> assertThat(postcodes.get(1).getLongitude()).isEqualTo(-3.412364),
                () -> assertThat(postcodes.get(2).getPostcode()).isEqualTo("CA9 3TW"),
                () -> assertThat(postcodes.get(2).getLatitude()).isEqualTo(54.81173),
                () -> assertThat(postcodes.get(2).getLongitude()).isEqualTo(-2.436008),
                () -> assertThat(postcodes.get(3).getPostcode()).isEqualTo("xxxxxxx"),
                () -> assertThat(postcodes.get(3).getLatitude()).isNull(),
                () -> assertThat(postcodes.get(3).getLongitude()).isNull()
        );
    }

    @Test
    @DisplayName("should return ordered list of warehouses by distance from postcode")
    void shouldReturnOrderedListOfWarehousesByDistanceFromPostcode() {
        //given
        var postcode = "e176pj";
        when(warehouseRepository.findAll()).thenReturn(List.of(
                Warehouse.builder().id(13L).name("Edmonton").postCode("N18 3AF").build(),
                Warehouse.builder().id(24L).name("Fulham").postCode("SW6 2ST").build(),
                Warehouse.builder().id(2L).name("Bromley").postCode("BR1 3RB").build(),
                Warehouse.builder().id(6L).name("Barking").postCode("IG11 8BL").build()

        ));
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
}

