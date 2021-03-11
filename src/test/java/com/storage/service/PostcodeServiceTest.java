package com.storage.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.storage.service.postcodes_api.PostcodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.storage.builders.MockDataForTest.createMapForBulkPostcodesRequest;
import static com.storage.constants.AppConstants.POSTCODE_BASE_CALL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PostcodeServiceTest {

    @Mock
    private PostcodeService service;

    @Test
    @DisplayName("should return true for valid postcode")
    void shouldReturnTrueForValidPostcode() {
        //given
        when(service.isValid(any(String.class))).thenReturn(true);
        //when
        var result = service.isValid("NW1 3LH");
        //then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"IP83NL","IP83NL/validate"})
    @DisplayName("should return 200 when sending correct single request postcode")
    void shouldReturn200WhenSendingCorrectSingleRequestPostcode(String variable) throws IOException, InterruptedException {
        //given
        var client = HttpClient.newBuilder().build();
        //when
        var request = HttpRequest
                .newBuilder(URI.create(POSTCODE_BASE_CALL+"/" +variable))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("should return 404 when sending bad request")
    void shouldReturn404WhenSendingBadRequest() throws IOException, InterruptedException {
        //given
        var client = HttpClient.newBuilder().build();
        //when
        var request = HttpRequest
                .newBuilder(URI.create(POSTCODE_BASE_CALL+"/NW13L"))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //then
        assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("should return 200 when sending correct bulk request for postcodes")
    void shouldReturn200WhenSendingCorrectBulkRequestForPostcodes() throws IOException, InterruptedException {
        //given
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
}
