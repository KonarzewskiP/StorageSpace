package com.storage.client;

import com.storage.exceptions.PostcodeClientException;
import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeResponse;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PostcodeClient {

    @Value("${postcode.api.url}")
    private String postcodeUrl;

    private final RestTemplate restTemplate;

    public PostcodeValidateDTO isValid(String postcode) {
        String url = String.format("%s/%s/validate", postcodeUrl, postcode);

        PostcodeValidateDTO postcodeValidateDTO;
        try {
            postcodeValidateDTO = restTemplate.getForObject(url, PostcodeValidateDTO.class);
        } catch (Exception e) {
            throw new PostcodeClientException("Postcode Client internal error!", e);
        }
        validate(postcodeValidateDTO);

        return postcodeValidateDTO;
    }

    public PostcodeDTO getDetails(String postcode) {
        String url = String.format("%s/%s", postcodeUrl, postcode);

        PostcodeDTO postcodeDTO;
        try {
            postcodeDTO = restTemplate.getForObject(url, PostcodeDTO.class);
        } catch (Exception e) {
            throw new PostcodeClientException("Postcode Client internal error!", e);
        }
        validate(postcodeDTO);

        return postcodeDTO;
    }

    private void validate(PostcodeResponse response) {
        if (response == null || (response.getStatus() == null && response.getError() == null))
            throw new PostcodeClientException("Response body is empty!");

        if (response.getStatus() < 200 || response.getStatus() >= 300)
            throw new PostcodeClientException(String.format("Postcode is invalid. Error msg: %s", response.getError()));
    }
}
