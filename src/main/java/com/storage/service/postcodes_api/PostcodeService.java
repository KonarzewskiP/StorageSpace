
package com.storage.service.postcodes_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.storage.exception.ResourceNotFoundException;
import com.storage.model.Warehouse;
import com.storage.model.dto.WarehouseDto;
import com.storage.model.mapper.ModelMapper;
import com.storage.model.postcodes_api.deserializer.PostcodeBulkResponseDeserializer;
import com.storage.model.postcodes_api.deserializer.PostcodeResponseDeserializer;
import com.storage.model.postcodes_api.deserializer.PostcodeValidationResponseDeserializer;
import com.storage.model.postcodes_api.response.PostcodeBulkResponse;
import com.storage.model.postcodes_api.response.PostcodeSingleResponse;
import com.storage.model.postcodes_api.response.ResultSingleResponse;
import com.storage.repository.WarehouseRepository;
import com.storage.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static com.storage.constants.AppConstants.*;


/**
 * Service Class that communicate with postcodes.io API and
 * provides methods for getting longitude/latitude coordinates
 * from specific postcode, validating postcode and calculating distance
 * between two postcodes.
 *
 * @author Pawel Konarzewski
 * @since 05/03/2021
 */


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostcodeService {

    private final WarehouseRepository warehouseRepository;

    private static final long TIMEOUT_IN_SECONDS = 10L;



    /**
     * The method that calls API and returns Boolean object.
     * <p>
     * Params: postcode - postcode from the UK
     * Returns: true is postcode is valid, otherwise
     *          throws ResourceNotFoundException.
     *
     * @author Pawel Konarzewski
     * @since 09/03/2021
     */


    public Boolean isValid(String postcode) {
        log.info("Enter PostcodeService -> isValid with: {}", postcode);

        try {
            var response = createGetResponseForValidation(postcode);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Boolean.class, new PostcodeValidationResponseDeserializer())
                    .setPrettyPrinting()
                    .create();
            var isPostcodeValid = gson.fromJson(response.body(), Boolean.class);

            if (isPostcodeValid) {
                return true;
            } else {
                throw new ResourceNotFoundException(POSTCODE, POSTCODE, postcode);
            }
        } catch (IOException exc) {
            log.error("Error while sending or receiving data.");
            throw new RuntimeException(exc.getMessage());
        } catch (InterruptedException exc){
            log.error("Error while sending request.");
            Thread.currentThread().interrupt();
            throw new RuntimeException(exc.getMessage());
        }

    }

    private HttpResponse<String> createGetResponseForValidation(String postcode) throws IOException, InterruptedException {
        log.info("Enter PostcodeService -> createGetResponse()  with: {}", postcode);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(createGetRequestForValidation(postcode), HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest createGetRequestForValidation(String postcode) {
        log.info("Enter PostcodeService -> createGetRequest() with: " + postcode);
        var url = POSTCODE_BASE_CALL + "/" + postcode.toUpperCase().replaceAll(" ", "").concat("/validate");
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
        log.info("Enter PostcodeService -> getLatAndLngForSinglePostcode() with: {}", postcode);
        try {
            var response = createGetResponse(postcode);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(PostcodeSingleResponse.class, new PostcodeResponseDeserializer())
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(response.body(), PostcodeSingleResponse.class);
        } catch (IOException | InterruptedException exc) {
            log.error("Error while sending request.");
            Thread.currentThread().interrupt();
            throw new RuntimeException(exc.getMessage());
        }
    }

    private HttpResponse<String> createGetResponse(String postcode) throws IOException, InterruptedException {
        log.info("Enter PostcodeService -> createGetResponse() with: {}", postcode);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(createGetRequest(postcode), HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest createGetRequest(String postcode) {
        log.info("Enter PostcodeService -> createGetRequest() with: " + postcode);
        var url = POSTCODE_BASE_CALL + "/" + postcode.toUpperCase().replaceAll(" ", "");
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS)) // HttpTimeoutException
                .GET()
                .build();
    }

    /**
     * The method that call postcodes.io API and returns PostcodeBulkResponse object with
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
        log.info("Enter PostcodeService -> getLatAndLngForManyPostcodes() with: " + postcodes);
        try {
            var response = createPostResponse(postcodes);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(PostcodeBulkResponse.class, new PostcodeBulkResponseDeserializer())
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(response.body(), PostcodeBulkResponse.class);
        } catch (IOException exc) {
            log.error("Error while sending or receiving data.");
            throw new RuntimeException(exc.getMessage());
        } catch (InterruptedException exc){
            log.error("Error while sending request.");
            Thread.currentThread().interrupt();
            throw new RuntimeException(exc.getMessage());
        }
    }

    private HttpResponse<String> createPostResponse(List<String> postcodes) throws IOException, InterruptedException {
        log.info("Enter PostcodeService -> createPostResponse() with: {}", postcodes);
        return HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(createPostRequest(postcodes), HttpResponse.BodyHandlers.ofString());
    }

    public HttpRequest createPostRequest(List<String> postcodes) {
        log.info("Enter PostcodeService -> createPostRequest() with: {}", postcodes);
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var map = Map.of("postcodes", postcodes);
        var json = gson.toJson(map);

        return HttpRequest.newBuilder()
                .uri(URI.create(POSTCODE_BASE_CALL))
                .version(HttpClient.Version.HTTP_2)
                .header("content-type", "application/json;charset=UTF-8")
                .timeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS)) // HttpTimeoutException
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    /**
     * Method that search for nearest Warehouses according to given postcode.
     *
     * @param postcode
     * @return ResponseEntity with a <code>List<WarehouseDto></code>. Max 4 WarehouseDto objects in the list.
     * @author Pawel Konarzewski
     * @since 09/03/2021
     */

    public List<WarehouseDto> getOrderedWarehousesByDistanceFromPostcode(String postcode) {
        log.info("Enter PostcodeService -> getNearestWarehouses() with: " + postcode);
        isValid(postcode);

        var warehousesList = warehouseRepository.findAll();
        var listOfPostcodesFromEachWarehouse = warehousesList
                .stream()
                .map(Warehouse::getPostCode)
                .collect(Collectors.toList());

        var userPostcodeCoordinates = getLatAndLngForSinglePostcode(postcode);
        var coordinatesOfWarehouses = getLatAndLngForManyPostcodes(listOfPostcodesFromEachWarehouse);
        var map = generateMapOfPostcodesWithDistanceFromPostcode(userPostcodeCoordinates, coordinatesOfWarehouses);
        LinkedHashMap<String, Double> sortedMap = sortPostcodesByDistance(map);
        List<Warehouse> orderedList = getOrderedListOfWarehouses(warehousesList, sortedMap);
        return ModelMapper.fromWarehouseListToWarehouseDtoList(orderedList);
    }

    private List<Warehouse> getOrderedListOfWarehouses(List<Warehouse> warehousesList, LinkedHashMap<String, Double> sortedMap) {
        return sortedMap.keySet().stream().map(aDouble -> {
            for (Warehouse x : warehousesList) {
                if (aDouble.equals(x.getPostCode())) {
                    return x;
                }
            }
            return null;
        }).collect(Collectors.toList());
    }

    private LinkedHashMap<String, Double> sortPostcodesByDistance(Map<String, Double> map) {
        return map
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    private Map<String, Double> generateMapOfPostcodesWithDistanceFromPostcode(PostcodeSingleResponse coordinatesForPostcode, PostcodeBulkResponse coordinatesOfWarehouses) {
        return coordinatesOfWarehouses.getResult().stream()
                .collect(Collectors.toMap(ResultSingleResponse::getPostcode,
                        storage -> Util.calculateDistance(storage, coordinatesForPostcode)));
    }


}

