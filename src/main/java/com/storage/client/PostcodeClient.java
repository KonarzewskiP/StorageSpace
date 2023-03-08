package com.storage.client;

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

        var response = restTemplate.getForEntity(url, PostcodeValidateDTO.class);

        return null;
    }


}
