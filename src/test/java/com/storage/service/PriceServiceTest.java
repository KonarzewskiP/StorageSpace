package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.models.Price;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import com.storage.models.enums.TimeUnit;
import com.storage.models.requests.CreatePriceRequest;
import com.storage.repositories.PriceRepository;
import com.storage.utils.mapper.PriceMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @InjectMocks
    private PriceService underTest;
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private WarehouseService warehouseService;
    @Mock
    private PriceMapper priceMapper;
    @Captor
    private ArgumentCaptor<Price> priceArgumentCaptor;
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

    @Nested
    class CreateTest {


        private final String WAREHOUSE_UUID = "warehouse-uuid";
        private final BigDecimal TELEPHONE_BOX_BASE_PRICE = new BigDecimal("10");
        private final BigDecimal LARGE_GARDEN_SHED = new BigDecimal("20");
        private final BigDecimal ONE_AND_HALF_GARAGES = new BigDecimal("30");
        private final BigDecimal THREE_SINGLE_GARAGES = new BigDecimal("40");
        private final BigDecimal UP_TO_2_WEEKS_MULTIPLIER = new BigDecimal("2");
        private final BigDecimal UP_TO_4_WEEKS_MULTIPLIER = new BigDecimal("1.9");
        private final BigDecimal UP_TO_8_WEEKS_MULTIPLIER = new BigDecimal("1.75");
        private final BigDecimal UP_TO_6_MONTHS_MULTIPLIER = new BigDecimal("1.6");
        private final BigDecimal UP_TO_1_YEAR_MULTIPLIER = new BigDecimal("1.5");
        private final BigDecimal PLUS_1_YEAR_MULTIPLIER = new BigDecimal("1.3");

        @Test
        void create() {
            //given
            CreatePriceRequest request = CreatePriceRequest.builder()
                    .warehouseUuid(WAREHOUSE_UUID)
                    .telephoneBoxBasePrice(TELEPHONE_BOX_BASE_PRICE)
                    .largeGardenShed(LARGE_GARDEN_SHED)
                    .oneAndHalfGarages(ONE_AND_HALF_GARAGES)
                    .threeSingleGarages(THREE_SINGLE_GARAGES)
                    .upTo2WeeksMultiplier(UP_TO_2_WEEKS_MULTIPLIER)
                    .upTo4WeeksMultiplier(UP_TO_4_WEEKS_MULTIPLIER)
                    .upTo8WeeksMultiplier(UP_TO_8_WEEKS_MULTIPLIER)
                    .upTo6MonthsMultiplier(UP_TO_6_MONTHS_MULTIPLIER)
                    .upTo1YearMultiplier(UP_TO_1_YEAR_MULTIPLIER)
                    .plus1YearMultiplier(PLUS_1_YEAR_MULTIPLIER)
                    .build();

            //... returned warehouse ID
            given(warehouseService.findIdByUuid(request.warehouseUuid())).willReturn(WAREHOUSE_ID);
            //when
            underTest.create(request);

            //then
            then(priceRepository).should(times(1)).save(priceArgumentCaptor.capture());
            Price savedPrice = priceArgumentCaptor.getValue();

            assertThat(savedPrice).isNotNull();
            assertAll(
                    () -> assertThat(savedPrice.getWarehouseId()).isEqualTo(WAREHOUSE_ID),
                    () -> assertThat(savedPrice.getTimeUnit()).isEqualTo(TimeUnit.WEEK),
                    () -> assertThat(savedPrice.getTelephoneBoxBasePrice()).isEqualTo(TELEPHONE_BOX_BASE_PRICE),
                    () -> assertThat(savedPrice.getLargeGardenShed()).isEqualTo(LARGE_GARDEN_SHED),
                    () -> assertThat(savedPrice.getOneAndHalfGarages()).isEqualTo(ONE_AND_HALF_GARAGES),
                    () -> assertThat(savedPrice.getThreeSingleGarages()).isEqualTo(THREE_SINGLE_GARAGES),
                    () -> assertThat(savedPrice.getUpTo2WeeksMultiplier()).isEqualTo(UP_TO_2_WEEKS_MULTIPLIER),
                    () -> assertThat(savedPrice.getUpTo4WeeksMultiplier()).isEqualTo(UP_TO_4_WEEKS_MULTIPLIER),
                    () -> assertThat(savedPrice.getUpTo8WeeksMultiplier()).isEqualTo(UP_TO_8_WEEKS_MULTIPLIER),
                    () -> assertThat(savedPrice.getUpTo6MonthsMultiplier()).isEqualTo(UP_TO_6_MONTHS_MULTIPLIER),
                    () -> assertThat(savedPrice.getUpTo1YearMultiplier()).isEqualTo(UP_TO_1_YEAR_MULTIPLIER),
                    () -> assertThat(savedPrice.getPlus1YearMultiplier()).isEqualTo(PLUS_1_YEAR_MULTIPLIER)
            );
        }
    }
}