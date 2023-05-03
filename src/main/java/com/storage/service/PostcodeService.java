
package com.storage.service;

import com.storage.client.PostcodeClient;
import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.PostcodeClientException;
import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import com.storage.utils.StringUtils;
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
     * Method that calls external API to verify if postcode is valid and exist.
     * <p>
     *
     * @param postcode postcode given by the user.
     * @return boolean. If postcode exist, returns true, otherwise false.
     * @throws PostcodeClientException
     */

    public PostcodeValidateDTO isValid(String postcode) {
        log.info("Validating postcode: {}", postcode);
        String formattedPostcode = format(postcode);

        if (!StringUtils.isPostcodeFormatValid(formattedPostcode))
            throw new BadRequestException(String.format("Postcode format is invalid! Invalid postcode: [%s]. Only UK postcodes can be validate!", postcode));

        return postcodeClient.isValid(formattedPostcode);
    }

    /**
     * Calls external API and returns details of the postcode.
     * <p>
     *
     * @param postcode postcode given by the user.
     * @return object with the longitude and latitude for postcode.
     * @throws PostcodeClientException
     */

    public PostcodeDTO getDetails(String postcode) {
        log.info("Getting details of postcode: {}", postcode);
        String formattedPostcode = format(postcode);

        if (!StringUtils.isPostcodeFormatValid(formattedPostcode))
            throw new BadRequestException(String.format("Postcode format is invalid! Invalid postcode: [%s]. Only UK postcodes can be validate!", postcode));

        return postcodeClient.getDetails(formattedPostcode);
    }

    private String format(String postcode) {
        if (isBlank(postcode))
            throw new BadRequestException("Postcode can not be empty or null");
        return postcode.replaceAll(" ", "").toUpperCase();
    }



}

