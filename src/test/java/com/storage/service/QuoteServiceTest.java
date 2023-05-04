package com.storage.service;

import com.storage.exceptions.ObjectValidationException;
import com.storage.models.Warehouse;
import com.storage.models.businessObject.Quote;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import com.storage.models.requests.QuoteEstimateRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.storage.models.enums.StorageDuration.PLUS_1_YEAR;
import static com.storage.models.enums.StorageSize.LARGE_GARDEN_SHED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
public class QuoteServiceTest {

    private static final String WAREHOUSE_UUID = "warehouseUuid";
    private static final LocalDate START_DATE = LocalDate.of(2030, 10, 12);
    private static final String FIRST_NAME = "Tony";
    private static final String LAST_NAME = "Hawk";
    private static final String EMAIL = "tony@hawk.com";
    private static final StorageSize STORAGE_SIZE = LARGE_GARDEN_SHED;
    private static final StorageDuration DURATION = PLUS_1_YEAR;
    @InjectMocks
    private QuoteService underTest;
    @Mock
    private WarehouseService warehouseService;
    @Mock
    private PriceService priceService;

    @Nested
    class EstimateTest {
        @Test
        void itShouldThrowErrorWhenEmailFormatIsInvalid() {
            //Given
            String invalidEmail = "abcdefgh.com";
            QuoteEstimateRequest request = QuoteEstimateRequest.builder()
                    .startDate(START_DATE)
                    .firstName(FIRST_NAME)
                    .lastName(LAST_NAME)
                    .duration(DURATION)
                    .storageSize(STORAGE_SIZE)
                    .email(invalidEmail)
                    .build();
            //When
            //Then
            assertThatThrownBy(() -> underTest.estimate(request))
                    .isInstanceOf(ObjectValidationException.class)
                    .hasMessageContaining("Email")
                    .hasMessageContaining("Has incorrect format. Email: " + invalidEmail);
        }

        @Test
        void itShouldSuccessfullyEstimate() {
            //Given
            QuoteEstimateRequest request = createQuoteEstimateRequest();
            // ... return warehouse by UUID
            Long warehouseId = 1L;
            String warehouseName = "BLUE";
            String city = "London";
            String street = "Prince rd";
            String postcode = "PO16 7GZ";
            Warehouse warehouse = Warehouse.builder()
                    .id(warehouseId)
                    .name(warehouseName)
                    .city(city)
                    .street(street)
                    .postcode(postcode)
                    .build();
            given(warehouseService.findByUuid(any())).willReturn(warehouse);
            // ... will return correct price
            BigDecimal price = BigDecimal.TEN;
            given(priceService.calculatePrice(STORAGE_SIZE, DURATION, warehouseId)).willReturn(price);
            //When
            Quote result = underTest.estimate(request);
            //Then

            assertThat(result).isNotNull();
            assertAll(
                    () -> assertThat(result.getFirstName()).isEqualTo(FIRST_NAME),
                    () -> assertThat(result.getLastName()).isEqualTo(LAST_NAME),
                    () -> assertThat(result.getEmail()).isEqualTo(EMAIL),
                    () -> assertThat(result.getPostcode()).isEqualTo(postcode),
                    () -> assertThat(result.getWarehouseName()).isEqualTo(warehouseName),
                    () -> assertThat(result.getCity()).isEqualTo(city),
                    () -> assertThat(result.getStreet()).isEqualTo(street),
                    () -> assertThat(result.getPrice()).isEqualTo(price),
                    () -> assertThat(result.getStorageSize()).isEqualTo(STORAGE_SIZE),
                    () -> assertThat(result.getStartDate()).isEqualTo(START_DATE),
                    () -> assertThat(result.getDuration()).isEqualTo(DURATION),
                    () -> assertThat(result.getExtraServices()).isNull()
            );
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
    }

}
