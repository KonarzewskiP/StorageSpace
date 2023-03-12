
package com.storage.service;

import com.storage.client.PostcodeClient;
import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.PostcodeClientException;
import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        postcode = formatAndValidate(postcode);

        return postcodeClient.isValid(postcode);
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
        formatAndValidate(postcode);

        return postcodeClient.getDetails(postcode);
    }

    /**
     * Helper method to validate if postcode is in valid format
     * <p>
     * NOTE: Only postcodes from the UK can be verified.
     */
    private String formatAndValidate(String postcode) {
        if (isBlank(postcode))
            throw new BadRequestException("Postcode can not be empty or null");
        String formattedPostcode = postcode.replaceAll(" ", "");

        Pattern pattern = Pattern.compile("^[A-Z]{1,2}[0-9R][0-9A-Z]?[0-9][ABD-HJLNP-UW-Z]{2}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(formattedPostcode);

        if (!matcher.find())
            throw new BadRequestException(String.format("Postcode format is invalid! Invalid postcode: [%s]. Only UK postcodes can be validate!", postcode));

        return formattedPostcode;
    }
}

