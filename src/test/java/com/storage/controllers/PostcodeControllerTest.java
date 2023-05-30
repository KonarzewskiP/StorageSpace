package com.storage.controllers;

import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeDetails;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import com.storage.service.PostcodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(PostcodeController.class)
class PostcodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostcodeService postcodeService;

    @Test
    @WithMockUser
    void itShouldValidatePostcode() throws Exception {
        //Given
        String postcode = "SW13PL";
        PostcodeValidateDTO postcodeValidateDTO = PostcodeValidateDTO.builder()
                .result(true)
                .build();
        given(postcodeService.isValid(postcode)).willReturn(postcodeValidateDTO);
        //When
        //Then
        mockMvc.perform(get("/api/v1/postcodes/{postcode}/valid", postcode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(Boolean.TRUE));
    }

    @Test
    @WithMockUser
    void itShouldReturnPostcodeDetails() throws Exception {
        //Given
        String postcode = "SW13PL";
        float lat = 51.4627f;
        float lng = -0.169f;
        // ... service return details of the postcode
        PostcodeDTO postcodeDTO = PostcodeDTO.builder()
                .status(200)
                .result(PostcodeDetails.builder()
                        .postcode(postcode)
                        .lat(lat)
                        .lng(lng)
                        .build())
                .build();
        given(postcodeService.getDetails(postcode)).willReturn(postcodeDTO);
        //When
        //Then
        mockMvc.perform(get("/api/v1/postcodes/{postcode}/details", postcode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.result.postcode").value(postcode))
                .andExpect(jsonPath("$.result.latitude").value(lat))
                .andExpect(jsonPath("$.result.longitude").value(lng));
    }
}