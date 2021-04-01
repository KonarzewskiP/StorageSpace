
package com.storage.service.postcodes_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.Warehouse;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.mapper.ModelMapper;
import com.storage.models.postcodes_api.response.*;
import com.storage.repositories.WarehouseRepository;
import com.storage.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Class that communicate with postcodes.io API and
 * provides methods for getting longitude/latitude coordinates
 * from specific postcode, validating postcode and calculating distance
 * between two postcodes.
 *
 * @author Pawel Konarzewski
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostcodeService {

    private static final String POSTCODE_BASE_CALL = "https://api.postcodes.io/postcodes";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WarehouseRepository warehouseRepository;

    /**
     * The method that calls API and returns Boolean object.
     * <p>
     * Params: postcode - postcode from the UK
     * Returns: PostcodeValidation object with status code and result.
     *
     * @author Pawel Konarzewski
     */

    public PostcodeValidationResponse isValid(String postcode) {

        String validPostcode = postcode.toUpperCase().replaceAll(" ", "").concat("/validate");
        ResponseEntity<PostcodeValidationResponse> response =
                restTemplate.getForEntity(POSTCODE_BASE_CALL + "/" + validPostcode, PostcodeValidationResponse.class);

        return response.getBody();
    }

    /**
     * The method that calls API and returns PostcodeResponse object
     * with coordinates for a single postcode.
     * <p>
     * Params: postcode - postcode from the UK
     * Returns: PostcodeResponse object with longitude and latitude coordinates.
     *
     * @author Pawel Konarzewski
     */

    public PostcodeResponse getCoordinatesPostcode(String postcode) {
        log.info("Enter PostcodeService -> getLatAndLngForSinglePostcode() with: {}", postcode);

        String validPostcode = postcode.toUpperCase().replaceAll(" ", "");
        ResponseEntity<PostcodeResponse> response =
                restTemplate.getForEntity(POSTCODE_BASE_CALL + "/" + validPostcode, PostcodeResponse.class);

        return response.getBody();
    }

    /**
     * The method that call postcodes.io API and returns PostcodeResponse object with
     * list of Result objects containing coordinates for many postcodes.
     * <p>
     * Params: postcodes - an object containing a list of postcodes from the UK
     * Returns: PostcodeResponse object with a list of Result objects.
     * The list contains postcode, longitudes and latitudes of specific postcode.
     *
     * @author Pawel Konarzewski
     */

    public PostcodeResponse getCoordinatesPostcodes(List<String> postcodes) {
        log.info("Enter PostcodeService -> getLatAndLngForManyPostcodes() with: " + postcodes);
        var map = Map.of("postcodes", postcodes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var json = objectMapper.valueToTree(map);
        HttpEntity<Object> request = new HttpEntity<>(json, headers);

        ResponseEntity<PostcodeResponse> response =
                restTemplate.postForEntity(POSTCODE_BASE_CALL, request, PostcodeResponse.class);
        return response.getBody();
    }


    /**
     * Method that search for nearest Warehouses according to given postcode.
     *
     * @param postcode
     * @return ResponseEntity with a <code>List<WarehouseDto></code>. Max 4 WarehouseDto objects in the list.
     * @author Pawel Konarzewski
     */
    public List<WarehouseDto> getOrderedWarehousesByDistanceFromPostcode(String postcode) {
        log.info("Enter PostcodeService -> getNearestWarehouses() with: " + postcode);
        isValid(postcode);

        var warehousesList = warehouseRepository.findAll();
        var listOfPostcodesFromEachWarehouse = warehousesList
                .stream()
                .map(Warehouse::getPostCode)
                .collect(Collectors.toList());

        var userPostcodeCoordinates = getCoordinatesPostcode(postcode);
        var coordinatesOfWarehouses = getCoordinatesPostcodes(listOfPostcodesFromEachWarehouse);
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

    private Map<String, Double> generateMapOfPostcodesWithDistanceFromPostcode(PostcodeResponse coordinatesForPostcode, PostcodeResponse coordinatesOfWarehouses) {
        return coordinatesOfWarehouses.getResult().stream()
                .collect(Collectors.toMap(Result::getPostcode,
                        storage -> Util.calculateDistance(storage, coordinatesForPostcode)));
    }


}

