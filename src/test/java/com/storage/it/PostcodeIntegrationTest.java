package com.storage.it;

import com.storage.exceptions.PostcodeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class that implements Postcode integration tests.
 *
 * @author Pawel Konarzewski
 */

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostcodeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should trigger no errors for valid postcode")
    void shouldTriggerNoErrorsForValidPostcode() throws Exception{
        //given
        String postcode = "SW178EF";
        //when
        var mockResult = mockMvc.perform(get("/postcodes/" + postcode + "/nearest"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("should trigger 404 status code for invalid postcode")
    void shouldTrigger404StatusCodeForInvalidPostcode() throws Exception {
        //given
        String postcode = "SW178";
        //when
        var mockResult = mockMvc.perform(get("/postcodes/" + postcode + "/nearest"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorMessage").value("Invalid postcode"))
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(PostcodeException.class));
    }
    }

