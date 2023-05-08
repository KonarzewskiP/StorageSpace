package com.storage.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.exceptions.PostcodeClientException;
import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeDetails;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


/**
 * From Postcode.io documentation
 * <p>
 * To check for errors, examine the HTTP response code. 200 response indicates success while
 * any other code will provide important information about why an error occurred.
 * <p>
 * Alternatively, you can examine status code in the 'status' property in the result body.
 * <p>
 * All JSONP requests return 200 responses because of silent errors.
 */
@RestClientTest(PostcodeClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class PostcodeClientTest {

    @Autowired
    private PostcodeClient underTest;
    @Autowired
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "https://api.postcodes.io/postcodes";
    private static final String POSTCODE = "GU206ET";


    @Nested
    class IsValidTest {

        @Test
        void itShouldThrowErrorWhenClientResponseInternalError() {
            //Given
            String url = String.format("%s/%s/validate", BASE_URL, POSTCODE);

            mockRestServiceServer
                    .expect(requestTo(url))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withServerError());
            //When
            //Then
            assertThatThrownBy(() -> underTest.isValid(POSTCODE))
                    .isInstanceOf(PostcodeClientException.class)
                    .hasMessageContaining("Postcode Client internal error!");

        }

        @Test
        void itShouldThrowErrorWhenResponseBodyIsEmpty() {
            //Given

            String url = String.format("%s/%s/validate", BASE_URL, POSTCODE);

            // ... response empty object
            String responseJson = objectToJson(new PostcodeValidateDTO());
            mockRestServiceServer
                    .expect(requestTo(url))
                    .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));
            //When
            //Then
            assertThatThrownBy(() -> underTest.isValid(POSTCODE))
                    .isInstanceOf(PostcodeClientException.class)
                    .hasMessageContaining("Response body is empty!");
        }

        @Test
        void itShouldThrowErrorWhenPostcodeIsInvalid() {
            //Given
            String url = String.format("%s/%s/validate", BASE_URL, POSTCODE);

            // ... response is with bad request status code
            PostcodeValidateDTO response = PostcodeValidateDTO.builder()
                    .status(404)
                    .error("Postcode not found")
                    .build();
            String responseJson = objectToJson(response);
            mockRestServiceServer
                    .expect(requestTo(url))
                    .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));
            //When
            //Then
            assertThatThrownBy(() -> underTest.isValid(POSTCODE))
                    .isInstanceOf(PostcodeClientException.class)
                    .hasMessageContaining(String.format("Postcode is invalid. Error msg: %s", response.getError()));
        }

        @Test
        void itShouldReturnTrueIfPostcodeIsValid() {
            //Given
            String url = String.format("%s/%s", BASE_URL, POSTCODE);

            // ... response success for valid postcode
            PostcodeDTO response = PostcodeDTO.builder()
                    .status(200)
                    .result(PostcodeDetails.builder()
                            .postcode(POSTCODE)
                            .lat(51.4627f)
                            .lng(-0.169f)
                            .build())
                    .build();

            String responseJson = objectToJson(response);
            mockRestServiceServer
                    .expect(requestTo(url))
                    .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));
            //When
            var result = underTest.getDetails(POSTCODE);
            //Then
            assertThat(result).isNotNull();
            assertThat(result).usingRecursiveComparison().isEqualTo(response);
        }
    }

    @Nested
    class GetDetailsTest {

        @Test
        void itShouldThrowErrorWhenClientResponseInternalError() {
            //Given
            String url = String.format("%s/%s", BASE_URL, POSTCODE);

            mockRestServiceServer
                    .expect(requestTo(url))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withServerError());
            //When
            //Then
            assertThatThrownBy(() -> underTest.getDetails(POSTCODE))
                    .isInstanceOf(PostcodeClientException.class)
                    .hasMessageContaining("Postcode Client error! Message: ");
        }

        @Test
        void itShouldReturnPostcodeDetails() {
            //Given
            String url = String.format("%s/%s/validate", BASE_URL, POSTCODE);

            // ... response success for valid postcode
            PostcodeValidateDTO response = PostcodeValidateDTO.builder()
                    .status(200)
                    .result(true)
                    .build();

            String responseJson = objectToJson(response);
            mockRestServiceServer
                    .expect(requestTo(url))
                    .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));
            //When
            PostcodeValidateDTO result = underTest.isValid(POSTCODE);
            //Then
            assertThat(result).isNotNull();
            assertThat(result).usingRecursiveComparison().isEqualTo(response);
        }
    }

    private String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to Json");
            return null;
        }
    }

}