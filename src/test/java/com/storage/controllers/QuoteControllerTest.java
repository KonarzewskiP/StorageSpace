package com.storage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.businessObject.Quote;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.service.EmailService;
import com.storage.service.QuoteService;
import com.storage.service.UserService;
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
import static com.storage.models.enums.StorageSize.LARGE_GARDEN_SHED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(QuoteController.class)
class QuoteControllerTest {
    private static final String WAREHOUSE_UUID = "warehouseUuid";
    private static final LocalDate START_DATE = LocalDate.of(2020, 10, 12);
    private static final String FIRST_NAME = "Tony";
    private static final String LAST_NAME = "Hawk";
    private static final String EMAIL = "tony@hawk.com";
    private static final StorageSize STORAGE_SIZE = LARGE_GARDEN_SHED;
    private static final StorageDuration DURATION = PLUS_1_YEAR;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private QuoteService quoteService;
    @MockBean
    private UserService userService;
    @MockBean
    private EmailService emailService;


    @Test
    void itShouldGenerateQuoteAndSaveANewUser() throws Exception {
        //Given
        QuoteEstimateRequest request = createQuoteEstimateRequest();
        Quote quote = createQuote();

        // ... return quote
        given(quoteService.estimate(request)).willReturn(quote);
        given(userService.isEmailTaken(request.email())).willReturn(false);

        //When + Then
        mockMvc.perform(post("/quote/estimation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isCreated());

        then(userService).should(times(1)).saveNewCustomer(any());
    }

    private Quote createQuote() {
        return Quote.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .postcode("123")
                .warehouseName("PURPLE")
                .city("NEW YORK")
                .street("Queens 12")
                .price(BigDecimal.TEN)
                .storageSize(STORAGE_SIZE)
                .startDate(START_DATE)
                .duration(DURATION)
                .extraServices(null)
                .build();
    }

    private QuoteEstimateRequest createQuoteEstimateRequest() {
        return QuoteEstimateRequest.builder()
                .warehouseUuid(WAREHOUSE_UUID)
                .startDate(START_DATE)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .storageSize(STORAGE_SIZE)
                .duration(DURATION)
                .extraServices(null)
                .build();
    }

    private <T> String toJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

}