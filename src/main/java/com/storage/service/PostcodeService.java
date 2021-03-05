package com.storage.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.storage.model.Location;
import com.storage.model.Warehouse;
import com.storage.repository.WarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
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
     * Method that call third party API and returns Location object for single postcode.
     * <p>
     * Params: postcode - postcode from UK
     * Returns: Location object with longitude and latitude coordinates. If Postcode
     * is invalid, then returns Location object with error property.
     *
     * @author Pawel Konarzewski
     * @since 05/03/2021
     */

    public static Location getLatAndLngForSinglePostcode(String postcode) throws URISyntaxException, InterruptedException {
        log.info("Enter getLatAndLngForSinglePostcode -> with: " + postcode);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Location> location = new ArrayList<>();

        createGetResponse(postcode).thenAccept(response -> {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            log.info("Enter createGetResponse -> with response: " + response.body());

            location.add(gson.fromJson(response.body(), Location.class));

            log.info("createGetResponse -> with location: " + location);
            countDownLatch.countDown();
        });

        countDownLatch.await();
        log.info(String.valueOf(location));
        log.info("2");

        return location.get(0);
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

/*    public static List<Location> getLatAndLngForManyPostcodes(String postcodes) throws URISyntaxException, InterruptedException {
        if (postcodes == null) {
            throw new IllegalArgumentException("Postcodes is null");
        }
//        CountDownLatch countDownLatch = new CountDownLatch(1);
        var postcodeList = createUrlToGetLocationOfWarehouses();
        final List<Location> codesList = new ArrayList<>();
        log.info(postcodeList);
        createPostResponse(postcodeList).thenAccept(response -> {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            codesList.addAll(gson.fromJson(response.body(), (Type) Location.class));
//            countDownLatch.countDown();
        });
//        countDownLatch.await();

        return codesList;
    }*/

    private static CompletableFuture<HttpResponse<String>> createPostResponse(String postcode) throws URISyntaxException {
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .sendAsync(createPostRequest(postcode), HttpResponse.BodyHandlers.ofString());
    }

    public static HttpRequest createPostRequest(String postcodes) throws URISyntaxException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(postcodes);

        return HttpRequest.newBuilder()
                .uri(new URI(postcodeBaseCall))
                .version(HttpClient.Version.HTTP_2)
                .header("content-type", "application/json;charset=UTF-8")
                .timeout(Duration.ofSeconds(10)) // HttpTimeoutException
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    private static String createUrlToGetLocationOfWarehouses() {
//        var warehousesList = warehouseRepository.findAll();

        var warehouse1 = Warehouse.builder().name("Croydon").postCode("CR0 3EU").build();
        var warehouse2 = Warehouse.builder().name("Barking").postCode("IG11 8BL").build();
        var warehouse3 = Warehouse.builder().name("Battersea ").postCode("SW11 3RX").build();
        List<Warehouse> warList = List.of(warehouse1, warehouse2, warehouse3);

        String postcodesList = warList.stream().map(Warehouse::getPostCode).collect(Collectors.joining(","));
        System.out.println(postcodesList);
        return postcodesList;
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



    }

}
