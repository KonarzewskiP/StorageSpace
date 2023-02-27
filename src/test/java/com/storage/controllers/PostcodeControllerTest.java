package com.storage.controllers;

import com.storage.models.dto.postcode.PostcodeValidateDTO;
import com.storage.service.PostcodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    void itShouldValidatePostcode() throws Exception {
        //Given
        String postcode = "SW1 3PL";
        PostcodeValidateDTO postcodeValidateDTO = new PostcodeValidateDTO();
        postcodeValidateDTO.setResult(true);

        given(postcodeService.isValid(postcode)).willReturn(postcodeValidateDTO);
        //When
        //Then
        mockMvc.perform(get("/postcodes/{postcode}/valid", postcode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(Boolean.TRUE));
    }

}