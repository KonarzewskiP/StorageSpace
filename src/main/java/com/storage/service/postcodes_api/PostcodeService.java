
package com.storage.service.postcodes_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.exceptions.PostcodeException;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
     * Method that calls external API to check if postcode given by the user is valid.
     *
     * NOTE: Only postcodes from the UK can be verified.
     * <p>
     *
     * @param postcode postcode given by the user.
     * @return PostcodeValidation object with status code and result.
     * If postcode exist, returns true, otherwise false.
     */

    public PostcodeValidationResponse isValid(String postcode) {
        log.info("Enter PostcodeService -> isValid() with: {}", postcode);

        String validPostcode = postcode.toUpperCase().replaceAll(" ", "").concat("/validate");
        ResponseEntity<PostcodeValidationResponse> response =
                restTemplate.getForEntity(postcodeUrl + "/" + validPostcode, PostcodeValidationResponse.class);

        return response.getBody();
    }

    /**
     * Calls external API and returns coordinates for postcode.
     * In case of invalid postcode restTemplate will throw an RestClientException which is
     * handled by RestTemplateErrorHandler class.
     * <p>
     * NOTE: Only postcodes from the UK can be verified.
     *
     * @param postcode postcode given by the user.
     * @throws PostcodeException if the postcode is invalid.
     * @return object with the longitude and latitude for postcode.
     *
     */

    public PostcodeResponse getCoordinatesPostcode(String postcode) {
        log.info("Enter PostcodeService -> getCoordinatesPostcode() with: {}", postcode);

        String validPostcode = postcode.toUpperCase().replaceAll(" ", "");
        ResponseEntity<PostcodeResponse> response =
                restTemplate.getForEntity(postcodeUrl + "/" + validPostcode, PostcodeResponse.class);
        return response.getBody();
    }

    /**
     * The method that calls external API and returns longitudes and latitudes of each postcode.
     * If a postcode is invalid, then returns null values, otherwise it returns data for
     * specific postcode.
     * <p>
     * Only postcodes from the UK can be verified.
     *
     * @param postcodes list of postcodes.
     * @return object holding list of the data containing longitudes and latitudes of each postcode.
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
     * The method that sort all warehouses from the database in ascending order by distance from
     * the postcode given by user in call to the controller method- getNearestWarehouses().
     *
     * @param postcode postcode given by the user.
     * @return list of ordered warehouses in descending order. From the closest to the most further away,
     * according to the postcode given by the user.
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
     * The method is to convert the list of warehouses from the database and
     * sorted postcodes of warehouses into an ordered list of warehouses.
     *
     * @param sortedPostcodesByDistance sorted postcodes by distance. Key is a string representing the
     *                                  postcode of the warehouse. Value is a double value representing the
     *                                  distance from the warehouse to the postcode given by the user in the
     *                                  call to the controller method - getNearestWarehouses().
     * @param warehousesList            list of all warehouse retrieved from the database.
     * @return list of warehouses ordered by distance from the postcode given by the user.
     */

    private List<Warehouse> getOrderedListOfWarehouses(List<Warehouse> warehousesList, LinkedHashMap<String, Double> sortedPostcodesByDistance) {
        log.info("Enter PostcodeService -> getOrderedListOfWarehouses()");
        return sortedPostcodesByDistance.keySet().stream().map(postcode -> {
            for (Warehouse warehouse : warehousesList) {
                if (postcode.equals(warehouse.getAddress().getPostcode())) {
                    return warehouse;
                }
            }
            return null;
        }).collect(Collectors.toList());
    }

    /**
     * The method is to sort in descending order postcodes of warehouses by distance from the postcode
     * given by the user.
     *
     * @param map data structure holding postcodes of warehouses as a key and distance
     *            from this postcode to the postcode given by the user.
     * @return map of ordered warehouse postcodes by distance from the postcode given by the user.
     */

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

    /**
     * The method is to get the calculated distance from each warehouse to the postcode given by the user.
     *
     * @param coordinatesForPostcode  coordinates of the postcode given by the user in the call to the controller
     *                                method - getNearestWarehouses().
     * @param coordinatesOfWarehouses coordinates of all warehouses from the database.
     * @return map of postcodes as a key and distance from the postcode given by the user as a value.
     */

    private Map<String, Double> getWarehousePostcodeByDistanceFromUserPostcode(PostcodeResponse coordinatesForPostcode, PostcodeResponseMany coordinatesOfWarehouses) {
        log.info("Enter PostcodeService -> getWarehousePostcodeByDistanceFromUserPostcode() ");
        return coordinatesOfWarehouses.getResult().stream()
                .collect(Collectors.toMap((ResultMany resultMany) -> resultMany.getResult().getPostcode(),
                        storage -> Util.calculateDistance(storage.getResult(), coordinatesForPostcode)));
    }


}

