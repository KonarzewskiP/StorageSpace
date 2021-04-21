
package com.storage.service.postcodes_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.Warehouse;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.dto.externals.postcode.PostcodeResponse;
import com.storage.models.dto.externals.postcode.PostcodeResponseMany;
import com.storage.models.dto.externals.postcode.PostcodeValidationResponse;
import com.storage.models.dto.externals.postcode.ResultMany;
import com.storage.models.mapper.ModelMapper;
import com.storage.repositories.WarehouseRepository;
import com.storage.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${postcode.api.url}")
    private String postcodeUrl;

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
        log.info("Enter PostcodeService -> isValid() with: {}", postcode);

        String validPostcode = postcode.toUpperCase().replaceAll(" ", "").concat("/validate");
        ResponseEntity<PostcodeValidationResponse> response =
                restTemplate.getForEntity(postcodeUrl + "/" + validPostcode, PostcodeValidationResponse.class);

        return response.getBody();
    }

    /**
     * The method that calls API and returns PostcodeResponse object
     * with coordinates for a single postcode. In case of invalid postcode restTemplate will throw
     * an error with 404 status code.
     * <p>
     * Params: postcode - postcode from the UK
     * Returns: PostcodeResponse object with longitude and latitude coordinates.
     *
     * @author Pawel Konarzewski
     */

    public PostcodeResponse getCoordinatesPostcode(String postcode) {
        log.info("Enter PostcodeService -> getCoordinatesPostcode() with: {}", postcode);

        String validPostcode = postcode.toUpperCase().replaceAll(" ", "");
        ResponseEntity<PostcodeResponse> response =
                restTemplate.getForEntity(postcodeUrl + "/" + validPostcode, PostcodeResponse.class);
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

    public PostcodeResponseMany getCoordinatesPostcodes(List<String> postcodes) {
        log.info("Enter PostcodeService -> getCoordinatesPostcodes() with: " + postcodes);
        var map = Map.of("postcodes", postcodes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var json = objectMapper.valueToTree(map);
        HttpEntity<Object> request = new HttpEntity<>(json, headers);

        return restTemplate.postForObject(postcodeUrl, request, PostcodeResponseMany.class);
    }

    /**
     * Method that sort warehouses by distance from user postcode.
     *
     * @param postcode
     * @return <code>List<WarehouseDto></code>.
     * @author Pawel Konarzewski
     */
    public List<WarehouseDto> getOrderedWarehousesByDistanceFromPostcode(String postcode) {
        log.info("Enter PostcodeService -> getOrderedWarehousesByDistanceFromPostcode() with: " + postcode);


        var warehousesList = warehouseRepository.findAll();
        var listOfPostcodesFromEachWarehouse = warehousesList
                .stream()
                .map(warehouse -> warehouse.getAddress().getPostcode())
                .collect(Collectors.toList());

        var coordinatesOfWarehouses = getCoordinatesPostcodes(listOfPostcodesFromEachWarehouse);
        var userPostcodeCoordinates = getCoordinatesPostcode(postcode);

        var map = getWarehousePostcodeByDistanceFromUserPostcode(userPostcodeCoordinates, coordinatesOfWarehouses);
        var sortedMap = sortPostcodesByDistance(map);
        var orderedList = getOrderedListOfWarehouses(warehousesList, sortedMap);
        return ModelMapper.fromWarehouseListToWarehouseDtoList(orderedList);
    }

    /**
     * Method that return list of Warehouses ordered by distance from postcode given by User.
     *
     * @param sortedMap - sorted map of warehouses by postcode. Key is a string representing postcode of warehouse.
     *                  Value is a double value representing distance from warehouse to the postcode given by user
     *                  in call to PostcodeController.
     * @return <code>List<Warehouse></code>.
     * @author Pawel Konarzewski
     */

    private List<Warehouse> getOrderedListOfWarehouses(List<Warehouse> warehousesList, LinkedHashMap<String, Double> sortedMap) {
        log.info("Enter PostcodeService -> getOrderedListOfWarehouses()");
        return sortedMap.keySet().stream().map(postcode -> {
            for (Warehouse x : warehousesList) {
                if (postcode.equals(x.getAddress().getPostcode())) {
                    return x;
                }
            }
            return null;
        }).collect(Collectors.toList());
    }

    private LinkedHashMap<String, Double> sortPostcodesByDistance(Map<String, Double> map) {
        log.info("Enter PostcodeService -> sortPostcodesByDistance() ");
        return map
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    private Map<String, Double> getWarehousePostcodeByDistanceFromUserPostcode(PostcodeResponse coordinatesForPostcode, PostcodeResponseMany coordinatesOfWarehouses) {
        log.info("Enter PostcodeService -> getWarehousePostcodeByDistanceFromUserPostcode() ");
        return coordinatesOfWarehouses.getResult().stream()
                .collect(Collectors.toMap((ResultMany resultMany) -> resultMany.getResult().getPostcode(),
                        storage -> Util.calculateDistance(storage.getResult(), coordinatesForPostcode)));
    }


}

