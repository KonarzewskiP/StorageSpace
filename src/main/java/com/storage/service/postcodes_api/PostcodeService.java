
package com.storage.service.postcodes_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.storage.model.postcodes_api.deserializer.PostcodeBulkResponseDeserializer;
import com.storage.model.postcodes_api.deserializer.PostcodeResponseDeserializer;
import com.storage.model.postcodes_api.deserializer.PostcodeValidationResponseDeserializer;
import com.storage.model.postcodes_api.response.PostcodeBulkResponse;
import com.storage.model.postcodes_api.response.PostcodeSingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Service Class that communicate with third party API and
 * provides methods for getting longitude/latitude coordinates
 * from specific postcode and calculating distance
 * between two postcodes.
 *
 * @author Pawel Konarzewski
 * @since 05/03/2021
 */


@Slf4j
@Component
public class PostcodeService {

    private static final long TIMEOUT_IN_SECONDS = 10L;

    private static String postcodeBaseCall = "https://api.postcodes.io/postcodes";

    public Boolean isValid(String postcode) {
        log.info("Enter PostcodeService -> isValid with: {}", postcode);

        try {
            var response = createGetResponseForValidation(postcode);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Boolean.class, new PostcodeValidationResponseDeserializer())
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(response.body(), Boolean.class);
        } catch (IOException | InterruptedException exc) {
            log.error("Error while sending request. Thread execution was interrupted.");
            throw new RuntimeException(exc.getMessage());
        }

    }

    private HttpResponse<String> createGetResponseForValidation(String postcode) throws IOException, InterruptedException {
        log.info("Enter createGetResponse -> with: {}", postcode);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(createGetRequestForValidation(postcode), HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest createGetRequestForValidation(String postcode) {
        log.info("Enter createGetRequest -> with: " + postcode);
        String url = postcodeBaseCall + "/" + postcode.toUpperCase().replaceAll(" ", "").concat("/validate");
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS))
                .GET()
                .build();
    }

    /**
     * The method that calls API and returns PostcodeSingleResponse object
     * with coordinates for a single postcode.
     * <p>
     * Params: postcode - postcode from the UK
     * Returns: PostcodeSingleResponse object with longitude and latitude coordinates. If Postcode
     * is invalid, then returns PostcodeSingleResponse object with error property.
     *
     * @author Pawel Konarzewski
     * @since 05/03/2021
     */

    public PostcodeSingleResponse getLatAndLngForSinglePostcode(String postcode) {
        log.info("Enter PostcodeService -> getLatAndLngForSinglePostcode with: {}", postcode);
        try {
            var response = createGetResponse(postcode);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(PostcodeSingleResponse.class, new PostcodeResponseDeserializer())
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(response.body(), PostcodeSingleResponse.class);
        } catch (IOException | InterruptedException exc) {
            log.error("Error while sending request. Thread execution was interrupted.");
            throw new RuntimeException(exc.getMessage());
        }
    }

    private HttpResponse<String> createGetResponse(String postcode) throws IOException, InterruptedException {
        log.info("Enter createGetResponse -> with: {}", postcode);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(createGetRequest(postcode), HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest createGetRequest(String postcode) {
        log.info("Enter createGetRequest -> with: " + postcode);
        String url = postcodeBaseCall + "/" + postcode.toUpperCase().replaceAll(" ", "");
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS)) // HttpTimeoutException
                .GET()
                .build();
    }

    /**
     * The method that call third party API and returns PostcodeBulkResponse object with
     * list of coordinate for many postcodes.
     * <p>
     * Params: postcodes - an object containing a list of postcodes from the UK
     * Returns: PostcodeBulkResponse object with a list of ResultSingleResponse objects.
     * The list contains longitudes and latitudes of specific postcode.
     *
     * @author Pawel Konarzewski
     * @since 05/03/2021
     */

    public PostcodeBulkResponse getLatAndLngForManyPostcodes(List<String> postcodes) {
        log.info("Enter getLatAndLngForManyPostcodes -> with: " + postcodes);
        try {
            var response = createPostResponse(postcodes);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(PostcodeBulkResponse.class, new PostcodeBulkResponseDeserializer())
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(response.body(), PostcodeBulkResponse.class);
        } catch (IOException | InterruptedException exc) {
            log.error("Error while sending request. Thread execution was interrupted.");
            throw new RuntimeException(exc.getMessage());
        }
    }

    private HttpResponse<String> createPostResponse(List<String> postcodes) throws IOException, InterruptedException {
        log.info("Enter createPostResponse -> with: {}", postcodes);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(createPostRequest(postcodes), HttpResponse.BodyHandlers.ofString());
    }

    public HttpRequest createPostRequest(List<String> postcodes) {
        log.info("Enter createPostRequest -> with: {}", postcodes);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, List<String>> map = new HashMap<>();
        map.put("postcodes", postcodes);
        String json = gson.toJson(map);

        return HttpRequest.newBuilder()
                .uri(URI.create(postcodeBaseCall))
                .version(HttpClient.Version.HTTP_2)
                .header("content-type", "application/json;charset=UTF-8")
                .timeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS)) // HttpTimeoutException
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }


}

