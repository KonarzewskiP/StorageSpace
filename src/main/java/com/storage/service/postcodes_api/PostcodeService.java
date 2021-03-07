package com.storage.service.postcodes_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.storage.model.postcodes_api.CustomDeserializer;
import com.storage.model.postcodes_api.PostcodeResponseDeserializer;
import com.storage.model.postcodes_api.PostcodeSingleResponse;
import com.storage.model.postcodes_api.PostcodeBulkRequest;
import com.storage.model.Warehouse;
import com.storage.repository.WarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;


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
//@RequiredArgsConstructor
//@AllArgsConstructor
public class PostcodeService {

    private static WarehouseRepository warehouseRepository;

    private static final long TIMEOUT_IN_SECONDS = 10L;

    private static String postcodeBaseCall = "https://api.postcodes.io/postcodes";

    /**
     * Method that call API and returns PostcodeSingleResponse object
     * with coordinates for single postcode.
     * <p>
     * Params: postcode - postcode from UK
     * Returns: PostcodeSingleResponse object with longitude and latitude coordinates. If Postcode
     * is invalid, then returns Location object with error property.
     *
     * @author Pawel Konarzewski
     * @since 05/03/2021
     */

    public static PostcodeSingleResponse getLatAndLngForSinglePostcode(String postcode) throws URISyntaxException, InterruptedException {
        log.info("Enter getLatAndLngForSinglePostcode -> with: " + postcode);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<PostcodeSingleResponse> postcodeSingleResponse = new ArrayList<>();

        createGetResponse(postcode).thenAccept(response -> {
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Gson gson = new GsonBuilder().registerTypeAdapter(PostcodeSingleResponse.class, new PostcodeResponseDeserializer()).setPrettyPrinting().create();
            log.info("Inside getLatAndLngForSinglePostcode -> with response: " + response.body());
            System.out.println();

            postcodeSingleResponse.add(gson.fromJson(response.body(), PostcodeSingleResponse.class));

            log.info("createGetResponse -> with location: " + postcodeSingleResponse);
            System.out.println();
            countDownLatch.countDown();
        });

        countDownLatch.await();

        log.info(String.valueOf(postcodeSingleResponse));

        return postcodeSingleResponse.get(0);
    }

    private static CompletableFuture<HttpResponse<String>> createGetResponse(String postcode) throws URISyntaxException {
        log.info("Enter createGetResponse -> with: " + postcode);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .sendAsync(createGetRequest(postcode), HttpResponse.BodyHandlers.ofString());
    }

    private static HttpRequest createGetRequest(String postcode) throws URISyntaxException {
        log.info("Enter createGetRequest -> with: " + postcode);
        String url = postcodeBaseCall + "/" + postcode.toUpperCase().replaceAll(" ", "");
        log.info((url));
        return HttpRequest.newBuilder()
                .uri(new URI(url))
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS)) // HttpTimeoutException
                .GET()
                .build();
    }

    /**
     * Method that call third party API and returns List of Location object for many postcodes.
     * <p>
     * Params: postcode - postcode from UK
     * Returns: Location object with longitude and latitude coordinates. If Postcode
     * is invalid, then returns Location object with error property.
     *
     * @author Pawel Konarzewski
     * @since 05/03/2021
     */

    public static PostcodeSingleResponse getLatAndLngForManyPostcodes(String postcodes) throws URISyntaxException, InterruptedException {
        if (postcodes == null) {
            throw new IllegalArgumentException("Postcodes is null");
        }
        log.info("Enter getLatAndLngForManyPostcodes -> with: " + postcodes);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        var postcodeList = createUrlToGetLocationOfWarehouses();
        final List<PostcodeSingleResponse> codesList = new ArrayList<>();

        log.info("Postcodes: " + postcodeList);
        createPostResponse(postcodeList).thenAccept(response -> {
            Gson gson = new GsonBuilder().registerTypeAdapter(PostcodeSingleResponse.class, new CustomDeserializer()).setPrettyPrinting().create();
            log.info("Response: " + response.body());
            codesList.add(gson.fromJson(response.body(), PostcodeSingleResponse.class));
            countDownLatch.countDown();
        });
        countDownLatch.await();

        return codesList.get(0);
    }

    private static CompletableFuture<HttpResponse<String>> createPostResponse(PostcodeBulkRequest postcode) throws URISyntaxException {
        log.info("Enter createPostResponse -> with: " + postcode);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .sendAsync(createPostRequest(postcode), HttpResponse.BodyHandlers.ofString());
    }

    public static HttpRequest createPostRequest(PostcodeBulkRequest postcodes) throws URISyntaxException {
        log.info("Enter createPostRequest -> with: " + postcodes);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(postcodes);
        log.info("Inside createPostRequest -> with: " + json);
        return HttpRequest.newBuilder()
                .uri(new URI(postcodeBaseCall))
                .version(HttpClient.Version.HTTP_2)
                .header("content-type", "application/json;charset=UTF-8")
//                .header("content-type", "application/json")
                .timeout(Duration.ofSeconds(10)) // HttpTimeoutException
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    private static PostcodeBulkRequest createUrlToGetLocationOfWarehouses() {
//        var warehousesList = warehouseRepository.findAll();
        log.info("Enter createUrlToGetLocationOfWarehouses -> ");
        var warehouse1 = Warehouse.builder().name("Croydon").postCode("CR0 3EU").build();
        var warehouse2 = Warehouse.builder().name("Barking").postCode("IG11 8BL").build();
        var warehouse3 = Warehouse.builder().name("Battersea ").postCode("SW11 3RX").build();
        List<Warehouse> warList = List.of(warehouse1, warehouse2, warehouse3);


        List<String> postcodesList = warList.stream().map(Warehouse::getPostCode).collect(Collectors.toList());
        var temp = PostcodeBulkRequest.builder().postcodes(postcodesList).build();
        System.out.println(postcodesList);
        return temp;
    }

    /**
     * Calculates the distance in km between two lat/long points
     * using the haversine formula
     * <p>
     * Params: lat1 - latitude of first postcode
     * lng1 - longitude of first postcode
     * lat2 - latitude of second postcode
     * lng2 - longitude of second postcode
     * Returns: distance between two postcodes in km
     *
     * @since 05/03/2021
     */

    public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        var earthRadius = 6371;
        var dLat = Math.toRadians(lat2 - lat1);
        var dLon = Math.toRadians(lng2 - lng1);
        var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        var d = earthRadius * c;
        return d;
    }


    public static void main(String[] args) throws URISyntaxException, InterruptedException {

        var location = getLatAndLngForSinglePostcode("SW96 AU");
        System.out.println(location);
//        var list = getLatAndLngForManyPostcodes("");
        System.out.println("-------------------------------------------------------------");
//        list.forEach(System.out::println);

    }

}
