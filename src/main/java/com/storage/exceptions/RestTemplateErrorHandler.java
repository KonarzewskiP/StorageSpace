package com.storage.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {


    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError() ){
            log.error("Client error with exception code: {} with message: {}", response.getStatusCode(), response.getStatusText());
        } else if (response.getStatusCode().is5xxServerError()) {
            log.error("Server error with exception code: {} with message: {}", response.getStatusCode(), response.getStatusText());
        } else {
            log.error("Unknown HttpStatusCode with exception code: {} with message: {}", response.getStatusCode(), response.getStatusText());
        }
        var objectMapper = new ObjectMapper();
        String errorMessage = objectMapper.readTree(response.getBody()).get("error").asText();

        throw new PostcodeException(response.getStatusCode(), errorMessage);
    }
}
