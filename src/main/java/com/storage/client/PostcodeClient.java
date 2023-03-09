package com.storage.client;

import com.storage.exceptions.PostcodeClientException;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PostcodeClient {

    @Value("${postcode.api.url}")
    private String postcodeUrl;

    private final RestTemplate restTemplate;

    public boolean isValid(String postcode) {
        String url = String.format("%s/%s/validate", postcodeUrl, postcode);

        ResponseEntity<PostcodeValidateDTO> responseEntity = restTemplate.getForEntity(url, PostcodeValidateDTO.class);
        if (responseEntity.getBody() == null || responseEntity.getBody().getStatus() == 500)
            throw new PostcodeClientException("Postcode Client internal error. Response body is empty! ");

        PostcodeValidateDTO postcodeValidateDTO = responseEntity.getBody();

        if (postcodeValidateDTO.getStatus() >= 200 && postcodeValidateDTO.getStatus() < 300)
            return postcodeValidateDTO.getResult();

        throw new PostcodeClientException(String.format("Postcode is invalid. Error msg: %s", postcodeValidateDTO.getError()));
    }


}
