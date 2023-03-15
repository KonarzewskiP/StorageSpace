package com.storage.service;

import com.storage.exceptions.NotFoundException;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import com.storage.repositories.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    @Test
    void itShouldReturnPrice() {
        //Given
        //When
        //Then
    }
}