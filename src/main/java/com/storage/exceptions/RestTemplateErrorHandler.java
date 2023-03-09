package com.storage.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//TODO - delete
// related to the postcodeService
@Slf4j
@Component
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        if (response.getStatusCode().is4xxClientError()) {
            log.error("Client error with exception code: {} with message: {}", response.getStatusCode(), response.getStatusText());
        } else if (response.getStatusCode().is5xxServerError()) {
            log.error("Server error with exception code: {} with message: {}", response.getStatusCode(), response.getStatusText());
        } else {
            log.error("Unknown HttpStatusCode with exception code: {} with message: {}", response.getStatusCode(), response.getStatusText());
        }
        String error = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

        throw new PostcodeClientException(error);
    }
}
