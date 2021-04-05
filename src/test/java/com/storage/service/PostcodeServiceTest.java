package com.storage.service;

import com.google.gson.GsonBuilder;
import com.storage.exceptions.PostcodeException;
import com.storage.models.postcodes_api.response.PostcodeResponse;
import com.storage.models.postcodes_api.response.PostcodeValidationResponse;
import com.storage.models.postcodes_api.response.Result;
import com.storage.repositories.WarehouseRepository;
import com.storage.service.postcodes_api.PostcodeService;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.storage.builders.MockDataForTest.createMapForBulkPostcodesRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostcodeServiceTest {

    @Value("${postcode.api.url}")
    private String postcodeUrl;

    @LocalServerPort
    private int port;

    @InjectMocks
    private PostcodeService service;

    @Autowired
    private RestTemplate restTemplate;

    @Mock
    private WarehouseRepository warehouseRepository;

    @ParameterizedTest
    @ValueSource(strings = {"SW178EF", "IP83NL"})
    @DisplayName("should return 200 when sending correct single request postcode")
    void shouldReturn200WhenSendingCorrectSingleRequestPostcode(String postcode) {
        //given
        //when
        var response = restTemplate.getForEntity("http://localhost:"
                + port + "/postcodes/" + postcode, PostcodeValidationResponse.class);
        //then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(Objects.requireNonNull(response.getBody()).getResult()).isTrue();
    }


    @Test
    @Ignore
    @DisplayName("should throw PostcodeException for invalid Postcode")
    void shouldThrowPostcodeExceptionForInvalidPostcode() {
/*
        For some reason while using ObjectMapper in RestTemplateErrorHandler
        objectMapper.readTree(response.getBody()).get("error").asText(); is invoked two times
        and while parsing json it throws an error, because second time response.getBody()).get("error") is null
        Meanwhile to make this test work you need to parse response body by using
        String error = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
*/
        //given
        var postcode = "NW1311";
        //when
        var thrown = catchThrowable(() -> restTemplate.getForEntity("http://localhost:"
                + port + "/postcodes/" + postcode + "/nearest", PostcodeException.class));
        //then
        assertThat(thrown)
                .isInstanceOf(PostcodeException.class);
    }

    @Test
    @Ignore
    @DisplayName("test2")
    void test2() {
        //given
        var postcode = "SW178EF";
        var postcodeResult = Result.builder()
                .postcode("SW178EF")
                .latitude(51.431047)
                .longitude(-0.155261)
                .build();
        var postcodeResponse = PostcodeResponse.builder().status(200).result(List.of(postcodeResult)).build();
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(postcodeResponse));
        //when
        var result = service.getCoordinatesPostcode(postcode);
        //then
        assertThat(result.getStatus()).isEqualTo(200);
    }


    @Test
    @Ignore
    @DisplayName("should return 404 when sending bad request for postcode validation")
    void shouldReturn404WhenSendingBadRequestForPostcodeValidation() {
        //given
        var postcode = "NW1311";
        //when
        var result = restTemplate.getForEntity("http://localhost:"
                + port + "/postcodes/" + postcode + "/nearest", PostcodeException.class);
        System.out.println("-----------------------------------------------------------");
        System.out.println(result.getStatusCodeValue());
        System.out.println(result);
        System.out.println("-----------------------------------------------------------");
        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    /*    assertThat(result)
                .isInstanceOf(PostcodeException.class);*/
    }


    @Test
    @DisplayName("should return 200 when sending correct request for many postcodes")
    void shouldReturn200WhenSendingCorrectRequestForManyPostcodes() {
        //given
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var postcodes = createMapForBulkPostcodesRequest();
        //when
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(gson.toJson(postcodes), headers);
        ResponseEntity<PostcodeResponse> response =
                restTemplate.postForEntity(postcodeUrl, request, PostcodeResponse.class);
        //then
        assertAll(
                () -> assertThat(response.getStatusCode().value()).isEqualTo(200),
                () -> assertThat(Objects.requireNonNull(response.getBody()).getResult().size()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("should throw PostcodeException when sending wrong request for post")
    void shouldThrowPostcodeExceptionWhenSendingWrongRequestForPost() {
        //given
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var postcodes = Map.of("", List.of("SE11 5QY", "SW19 3BE", "TW9 2JX"));
        //when
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(gson.toJson(postcodes), headers);
        var thrown = catchThrowable(() ->
                restTemplate.postForEntity(postcodeUrl, request, PostcodeResponse.class));
        //then
        assertThat(thrown)
                .isInstanceOf(PostcodeException.class);
    }

    @Test
    @Ignore
    @DisplayName("should return object with list of coordinates for many postcodes")
    void shouldReturnObjectWithListOfCoordinatesForManyPostcodes() {
        //given
        var listOfPostcodes = List.of("CM16 4BL", "CF399BQ", "ca9 3tw", "xxxxxxx");
        //when
        var result = service.getCoordinatesPostcodes(listOfPostcodes);
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

/*    @Test
    @Ignore
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
//        var result = service.getOrderedWarehousesByDistanceFromPostcode(postcode);
        var response = restTemplate.getForEntity("http://localhost:"
                + port + "/postcodes/" + postcode, WarehouseDto[].class);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(response);
        System.out.println("-----------------------------------------------------------------");
        var result = Arrays.stream(Objects.requireNonNull(response.getBody())).collect(Collectors.toList());
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
    }*/
}

