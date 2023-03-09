
package com.storage.service;

import com.storage.client.PostcodeClient;
import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.PostcodeClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.isBlank;

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

    private final PostcodeClient postcodeClient;

    /**
     * Method that calls external API to check if postcode given by the user is valid.
     * <p>
     * NOTE: Only postcodes from the UK can be verified.
     * <p>
     *
     * @param postcode postcode given by the user.
     * @return PostcodeValidation object with status code and result.
     * If postcode exist, returns true, otherwise false.
     */

    public boolean isValid(String postcode) {
        if (isBlank(postcode))
            throw new BadRequestException("Postcode can not be empty or null");

        String validPostcode = postcode.toUpperCase().replaceAll(" ", "");

        return postcodeClient.isValid(validPostcode);
    }

    /**
     * Calls external API and returns coordinates for postcode.
     * In case of invalid postcode restTemplate will throw an RestClientException which is
     * handled by RestTemplateErrorHandler class.
     * <p>
     * NOTE: Only postcodes from the UK can be verified.
     *
     * @param postcode postcode given by the user.
     * @return object with the longitude and latitude for postcode.
     * @throws PostcodeClientException if the postcode is invalid.
     */

    //TODO to implement
//    public PostcodeDTO getSingleCoordinates(String postcode) {
//        log.info("Enter PostcodeService -> getCoordinatesPostcode() with: {}", postcode);
//
//        String validPostcode = postcode.toUpperCase().replaceAll(" ", "");
//        ResponseEntity<PostcodeDTO> response =
//                restTemplate.getForEntity(postcodeUrl + "/" + validPostcode, PostcodeDTO.class);
//        return response.getBody();
//    }

}

