package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.models.Price;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import com.storage.models.enums.TimeUnit;
import com.storage.repositories.PriceRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @InjectMocks
    private PriceService underTest;
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private WarehouseService warehouseService;

    private static final Long WAREHOUSE_ID = 100L;

    @Nested
    class CalculatePriceTest {
        @Test
        void itShouldThrowErrorWhenNoPriceForSpecificWarehouse() {
            //Given
            given(priceRepository.getByWarehouseId(WAREHOUSE_ID)).willReturn(Optional.empty());
            //... return uuid for the warehouse
            String warehouseUuid = "warehouse-uuid";
            given(warehouseService.findUuidById(WAREHOUSE_ID)).willReturn(warehouseUuid);
            //When
            //Then
            assertThatThrownBy(() -> underTest.calculatePrice(StorageSize.ONE_AND_HALF_GARAGES, StorageDuration.UP_TO_4_WEEKS, WAREHOUSE_ID))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining("Price table not found for warehouse with UUID: " + warehouseUuid);
        }

        private static Stream<Arguments> provideDataForTheSmallestRoom() {
            return Stream.of(
                    Arguments.of(StorageDuration.UP_TO_2_WEEKS, new BigDecimal("100.00")),
                    Arguments.of(StorageDuration.UP_TO_4_WEEKS, new BigDecimal("95.00")),
                    Arguments.of(StorageDuration.UP_TO_8_WEEKS, new BigDecimal("85.00")),
                    Arguments.of(StorageDuration.UP_TO_6_MONTHS, new BigDecimal("70.00")),
                    Arguments.of(StorageDuration.UP_TO_1_YEAR, new BigDecimal("50.00"))
            );
        }

        @ParameterizedTest
        @MethodSource("provideDataForTheSmallestRoom")
        void itShouldReturnPriceForTheSmallestRoom(StorageDuration duration, BigDecimal expected) {
            //Given
            Price price = Price.builder()
                    .warehouseId(WAREHOUSE_ID)
                    .timeUnit(TimeUnit.WEEK)
                    .telephoneBoxBasePrice(new BigDecimal("50.00"))
                    .upTo2WeeksMultiplier(new BigDecimal("2.0"))
                    .upTo4WeeksMultiplier(new BigDecimal("1.9"))
                    .upTo8WeeksMultiplier(new BigDecimal("1.7"))
                    .upTo6MonthsMultiplier(new BigDecimal("1.4"))
                    .upTo1YearMultiplier(new BigDecimal("1"))
                    .build();

            // ... return price table
            given(priceRepository.getByWarehouseId(WAREHOUSE_ID)).willReturn(Optional.of(price));
            //When
            BigDecimal result = underTest.calculatePrice(StorageSize.TELEPHONE_BOX, duration, WAREHOUSE_ID);
            //Then
            assertThat(result).isEqualByComparingTo(expected);
        }

        private static Stream<Arguments> provideDataForTheShortestDuration() {
            return Stream.of(
                    Arguments.of(StorageSize.TELEPHONE_BOX, new BigDecimal("100.00")),
                    Arguments.of(StorageSize.LARGE_GARDEN_SHED, new BigDecimal("150.00")),
                    Arguments.of(StorageSize.ONE_AND_HALF_GARAGES, new BigDecimal("200.00")),
                    Arguments.of(StorageSize.THREE_SINGLE_GARAGES, new BigDecimal("330.00"))
            );
        }

        @ParameterizedTest
        @MethodSource("provideDataForTheShortestDuration")
        void itShouldReturnPriceForTheShortestDuration(StorageSize size, BigDecimal expected) {
            //Given
            Price price = Price.builder()
                    .warehouseId(WAREHOUSE_ID)
                    .timeUnit(TimeUnit.WEEK)
                    .telephoneBoxBasePrice(new BigDecimal("50.00"))
                    .largeGardenShed(new BigDecimal("75.00"))
                    .oneAndHalfGarages(new BigDecimal("100.00"))
                    .threeSingleGarages(new BigDecimal("165.00"))
                    .upTo2WeeksMultiplier(new BigDecimal("2.0"))
                    .build();

            // ... return price table
            given(priceRepository.getByWarehouseId(WAREHOUSE_ID)).willReturn(Optional.of(price));
            //When
            BigDecimal result = underTest.calculatePrice(size, StorageDuration.UP_TO_2_WEEKS, WAREHOUSE_ID);
            //Then
            assertThat(result).isEqualByComparingTo(expected);
        }

        @Test
        void itShouldThrowErrorWhenStorageDurationIsLongerThan1Year() {
            //Given
            Price price = Price.builder().build();
            // ... return price table
            given(priceRepository.getByWarehouseId(WAREHOUSE_ID)).willReturn(Optional.of(price));
            //When
            //Then
            assertThatThrownBy(() -> underTest.calculatePrice(StorageSize.ONE_AND_HALF_GARAGES, StorageDuration.PLUS_1_YEAR, WAREHOUSE_ID))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining("Individual Pricing. Contact Customer Service.");

        }
    }
}