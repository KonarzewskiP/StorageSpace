package com.storage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.businessObject.Quote;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.storage.models.enums.StorageDuration.PLUS_1_YEAR;
import static com.storage.models.enums.StorageSize.LARGE_DOUBLE_GARAGE;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(QuoteController.class)
class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuoteService quoteService;

    private static final String WAREHOUSE_UUID = "warehouseUuid";
    private static final LocalDate START_DATE = LocalDate.of(2020, 10, 12);
    private static final String FIRST_NAME = "Tony";
    private static final String LAST_NAME = "Hawk";
    private static final String EMAIL = "tony@hawk.com";
    private static final StorageSize STORAGE_SIZE = LARGE_DOUBLE_GARAGE;
    private static final StorageDuration DURATION = PLUS_1_YEAR;

    @Test
    void itShouldValidatePostcode() throws Exception {
        //Given
        QuoteEstimateRequest estimation = createQuoteEstimateRequest();
        Quote quote = createQuote();

        // ... return quote
        given(quoteService.estimate(estimation)).willReturn(quote);

        //When
        //Then
        mockMvc.perform(post("/quote/estimation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(estimation)))
                .andExpect(status().isCreated());
    }

    private Quote createQuote() {
        return new Quote(
                FIRST_NAME,
                LAST_NAME,
                EMAIL,
                "123",
                "PURPLE",
                "NEW YORK",
                "Queens 12",
                BigDecimal.TEN,
                BigDecimal.TEN,
                STORAGE_SIZE,
                START_DATE,
                DURATION,
                null);
    }

    private QuoteEstimateRequest createQuoteEstimateRequest() {
        return new QuoteEstimateRequest(
                WAREHOUSE_UUID,
                START_DATE,
                FIRST_NAME,
                LAST_NAME,
                EMAIL,
                STORAGE_SIZE,
                DURATION,
                null);
    }

    private <T> String toJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

}