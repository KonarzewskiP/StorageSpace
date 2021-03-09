package com.storage.service.postcodes_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.storage.model.postcodes_api.deserializer.PostcodeBulkResponseDeserializer;
import com.storage.model.postcodes_api.deserializer.PostcodeResponseDeserializer;
import com.storage.model.postcodes_api.response.PostcodeBulkResponse;
import com.storage.model.postcodes_api.response.PostcodeSingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;


/**
 * Service Class that communicate with third party API and
 * provides methods for getting longitude/latitude coordinates
 * from specific postcode and calculating distance
 * from two postcodes.
 *
 * @author Pawel Konarzewski
 * @since 05/03/2021
 */

@Slf4j
@Component
public class PostcodeService {

    private static final long TIMEOUT_IN_SECONDS = 10L;

    private static String postcodeBaseCall = "https://api.postcodes.io/postcodes";

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

    public static PostcodeSingleResponse getLatAndLngForSinglePostcode(String postcode) {
        log.info("Enter getLatAndLngForSinglePostcode -> with: {}", postcode);
        final List<PostcodeSingleResponse> postcodeSingleResponse = new ArrayList<>();

        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            createGetResponse(postcode).thenAccept(response -> {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(PostcodeSingleResponse.class, new PostcodeResponseDeserializer())
                        .setPrettyPrinting()
                        .create();
                postcodeSingleResponse.add(gson.fromJson(response.body(), PostcodeSingleResponse.class));
                countDownLatch.countDown();
            });
            countDownLatch.await();
        } catch (InterruptedException exc) {
            log.error("Error while sending request. Thread execution was interrupted.");
            Thread.currentThread().interrupt();
            throw new RuntimeException(exc.getMessage());
        }
        return postcodeSingleResponse.get(0);
    }

    private static CompletableFuture<HttpResponse<String>> createGetResponse(String postcode) {
        log.info("Enter createGetResponse -> with: {}", postcode);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .sendAsync(createGetRequest(postcode), HttpResponse.BodyHandlers.ofString());
    }

    private static HttpRequest createGetRequest(String postcode) {
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

    public static PostcodeBulkResponse getLatAndLngForManyPostcodes(List<String> postcodes) {
        log.info("Enter getLatAndLngForManyPostcodes -> with: " + postcodes);
        final List<PostcodeBulkResponse> codesList = new ArrayList<>();

        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            createPostResponse(postcodes).thenAccept(response -> {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(PostcodeBulkResponse.class, new PostcodeBulkResponseDeserializer())
                        .setPrettyPrinting()
                        .create();
                codesList.add(gson.fromJson(response.body(), PostcodeBulkResponse.class));
                countDownLatch.countDown();
            });
            countDownLatch.await();
        } catch (InterruptedException exc) {
            log.error("Error while sending request. Thread execution was interrupted.");
            Thread.currentThread().interrupt();
            throw new RuntimeException(exc.getMessage());
        }
        return codesList.get(0);
    }

    private static CompletableFuture<HttpResponse<String>> createPostResponse(List<String> postcodes) {
        log.info("Enter createPostResponse -> with: {}", postcodes);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .sendAsync(createPostRequest(postcodes), HttpResponse.BodyHandlers.ofString());
    }

    public static HttpRequest createPostRequest(List<String> postcodes) {
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


    public static void main(String[] args) {

//        var location = getLatAndLngForSinglePostcode("SW96 AU");
//        System.out.println(location);
        List<String> postcodesList = List.of("CR0 3EU33", "IG11 8BL", "SW113RX");
        var coordinates = getLatAndLngForManyPostcodes(postcodesList);
        System.out.println("-------------------------------------------------------------");
//        list.forEach(System.out::println);
        coordinates.getResult().forEach(System.out::println);

    }

}
