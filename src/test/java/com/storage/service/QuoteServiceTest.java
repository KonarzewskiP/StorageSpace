package com.storage.service;

import com.storage.exceptions.EmailException;
import com.storage.models.requests.QuoteEstimateRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(SpringExtension.class)
public class QuoteServiceTest {

    @InjectMocks
    private QuoteService underTest;
    @Mock
    private  WarehouseService warehouseService;
    @Mock
    private  PriceService priceService;

    @Nested
    class EstimateTest {

        @Test
        void itShouldThrowErrorWhenEmailFormatIsInvalid() {
            //Given
            String invalidEmail = "abcdefgh.com";
            QuoteEstimateRequest request = QuoteEstimateRequest.builder()
                    .email(invalidEmail)
                    .build();
            //When
            //Then
            assertThatThrownBy(() -> underTest.estimate(request))
                    .isInstanceOf(EmailException.class)
                    .hasMessageContaining(String.format("Email [%s] has invalid format!", invalidEmail));
        }
    }

}
