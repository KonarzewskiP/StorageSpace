
package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.PostcodeException;
import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    @Value("${postcode.api.url}")
    private String postcodeUrl;

    private final RestTemplate restTemplate;

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

    public PostcodeValidateDTO isValid(String postcode) {
        if (isBlank(postcode))
            throw new BadRequestException("Postcode can not be empty or null");

        String validPostcode = postcode.toUpperCase().replaceAll(" ", "").concat("/validate");

        ResponseEntity<PostcodeValidateDTO> response =
                restTemplate.getForEntity(postcodeUrl + "/" + validPostcode, PostcodeValidateDTO.class);

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
     * @return object with the longitude and latitude for postcode.
     * @throws PostcodeException if the postcode is invalid.
     */

    public PostcodeDTO getSingleCoordinates(String postcode) {
        log.info("Enter PostcodeService -> getCoordinatesPostcode() with: {}", postcode);

        String validPostcode = postcode.toUpperCase().replaceAll(" ", "");
        ResponseEntity<PostcodeDTO> response =
                restTemplate.getForEntity(postcodeUrl + "/" + validPostcode, PostcodeDTO.class);
        return response.getBody();
    }

}

