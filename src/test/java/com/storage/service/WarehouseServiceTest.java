package com.storage.service;

import com.storage.exceptions.ResourceNotFoundException;
import com.storage.exceptions.WarehouseServiceException;
import com.storage.models.Address;
import com.storage.models.Warehouse;
import com.storage.models.dto.WarehouseDto;
import com.storage.repositories.StorageRoomRepository;
import com.storage.repositories.WarehouseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.storage.builders.MockDataForTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class WarehouseServiceTest {

    @InjectMocks
    private WarehouseService service;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private StorageRoomRepository storageRoomRepository;

    @Test
    @DisplayName("should add warehouse to repository")
    void shouldAddWarehouseToRepository() {
        //given
        var warehouse = createWarehouse();
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);
        var testWarehouseDto = WarehouseDto.builder()
                .name("Fake Name")
                .address(Address.builder()
                        .city("London")
                        .street("Ashton Road")
                        .postcode("RM3 8NF")
                        .build())
                .build();
        //when
        var result = service.addWarehouse(testWarehouseDto);
        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(2L),
                () -> assertThat(result.getName()).isEqualTo("Big Yellow")
        );


    }

    @Test
    @DisplayName("should throw WarehouseServiceException when warehouseDto is null")
    void shouldThrowWarehouseServiceExceptionWhenWarehouseDtoIsNull() {
        //given
        WarehouseDto warehouseDto = null;
        //when
        Throwable thrown = catchThrowable(() -> service.addWarehouse(warehouseDto));
        //then
        assertThat(thrown)
                .isInstanceOf(WarehouseServiceException.class)
                .hasMessageContaining("Invalid WarehouseDto!");
    }

    @Test
    @DisplayName("should throw WarehouseServiceException when warehouseDto is invalid")
    void shouldThrowWarehouseServiceExceptionWhenWarehouseDtoIsInvalid() {
        var warehouseDto = WarehouseDto.builder()
                .name("")
                .address(Address.builder()
                        .city("")
                        .street("")
                        .postcode("RM3 8NF1")
                        .build())
                .build();
        //when
        Throwable thrown = catchThrowable(() -> service.addWarehouse(warehouseDto));
        //then
        assertThat(thrown)
                .isInstanceOf(WarehouseServiceException.class)
                .hasMessageContaining("Invalid WarehouseDto!")
                .hasMessageContaining("Name")
                .hasMessageContaining("City")
                .hasMessageContaining("Street")
                .hasMessageContaining("Postcode")
                .hasMessageContaining("Can not be empty")
                .hasMessageContaining("Has incorrect format");
    }

    @Test
    @DisplayName("should find warehouse by id")
    void should() {
        //given
        var warehouse = createWarehouse();
        when(warehouseRepository.findById(anyLong())).thenReturn(Optional.of(warehouse));
        //when
        var result = service.getWarehouseById(999L);
        //then
        assertThat(result.getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException when id is null")
    void shouldThrowResourceNotFoundExceptionWhenIdIsNull() {
        //given
        Long id = null;
        //when
        Throwable thrown = catchThrowable(() -> service.getWarehouseById(id));
        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Warehouse not found with id: null");
    }

    @Test
    @DisplayName("should throw ResourceNotFoundError when can not find warehouse by id")
    void shouldThrowResourceNotFoundErrorWhenCanNotFindWarehouseById() {
        //given
        var id = 999L;
        //when
        Throwable thrown = catchThrowable(() -> service.getWarehouseById(id));
        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Warehouse not found with id: 999");
    }

    @Test
    @DisplayName("should return not reserved storage rooms from warehouse by id")
    void shouldReturnNotReservedStorageRoomsFromWarehouseById() {
        //given
        var warehouse = createWarehouse();
        when(warehouseRepository.findById(anyLong())).thenReturn(Optional.of(warehouse));
        var id = 10L;
        //when
        var result = service.getNotReservedStorageRoomsByWarehouseId(id);
        //then
        assertThat(result).hasSize(12);
    }

    @Test
    @DisplayName("should return ResourceNotFoundException when can not find warehouse by id passed to method")
    void shouldReturnResourceNotFoundExceptionWhenCanNotFindWarehouseByIdPassedToMethod() {
        //given
        var id = 999L;
        //when
        Throwable thrown = catchThrowable(() -> service.getNotReservedStorageRoomsByWarehouseId(id));
        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Warehouse not found with id: 999");
    }


}
