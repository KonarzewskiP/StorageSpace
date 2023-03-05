package com.storage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.Warehouse;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.requests.CreateWarehouseRequest;
import com.storage.service.StorageRoomService;
import com.storage.service.WarehouseService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(WarehouseController.class)
class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StorageRoomService storageRoomService;
    @MockBean
    private WarehouseService warehouseService;

    private static final String WAREHOUSE_UUID = "warehouse-uuid";
    private static final String NAME = "Orange";
    private static final String CITY = "London";
    private static final String POSTCODE = "SE11 4UB";
    private static final String STREET = "57 Walcot Square";
    private static final float LAT = 51.4943f;
    private static final float LNG = -0.1089f;

    @Nested
    class CreateTest {
        @Test
        void itShouldCreateANewUser() throws Exception {
            //Given
            CreateWarehouseRequest createWarehouseRequest = new CreateWarehouseRequest();
            createWarehouseRequest.setName(NAME);
            createWarehouseRequest.setCity(CITY);
            createWarehouseRequest.setPostcode(POSTCODE);
            createWarehouseRequest.setStreet(STREET);
            //... return new warehouse
            WarehouseDto warehouseDto = WarehouseDto.builder()
                    .uuid(WAREHOUSE_UUID)
                    .name(NAME)
                    .city(CITY)
                    .postcode(POSTCODE)
                    .street(STREET)
                    .lat(LAT)
                    .lng(LNG)
                    .build();
            given(warehouseService.addWarehouse(createWarehouseRequest)).willReturn(warehouseDto);
            //When
            ResultActions result = mockMvc.perform(post("/warehouses")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(toJson(createWarehouseRequest)));
            //Then
            result.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.uuid").value(WAREHOUSE_UUID))
                    .andExpect(jsonPath("$.name").value(NAME))
                    .andExpect(jsonPath("$.city").value(CITY))
                    .andExpect(jsonPath("$.postcode").value(POSTCODE))
                    .andExpect(jsonPath("$.street").value(STREET))
                    .andExpect(jsonPath("$.lat").value(LAT))
                    .andExpect(jsonPath("$.lng").value(LNG));
        }
    }

    @Nested
    class GetByUuidTest {
        @Test
        void itShouldGetByUuid() throws Exception {
            //Given
            //... return warehouse by UUID
            Warehouse warehouse = Warehouse.builder()
                    .uuid(WAREHOUSE_UUID)
                    .name(NAME)
                    .city(CITY)
                    .postcode(POSTCODE)
                    .street(STREET)
                    .lat(LAT)
                    .lng(LNG)
                    .totalRentalAreaInM2(BigDecimal.TEN)
                    .build();
            given(warehouseService.findByUuid(WAREHOUSE_UUID)).willReturn(warehouse);
            //When
            ResultActions result = mockMvc.perform(get("/warehouses/{uuid}", WAREHOUSE_UUID)
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.uuid").value(WAREHOUSE_UUID))
                    .andExpect(jsonPath("$.name").value(NAME))
                    .andExpect(jsonPath("$.city").value(CITY))
                    .andExpect(jsonPath("$.postcode").value(POSTCODE))
                    .andExpect(jsonPath("$.street").value(STREET))
                    .andExpect(jsonPath("$.lat").value(LAT))
                    .andExpect(jsonPath("$.lng").value(LNG));
        }
    }

    @Nested
    class GetAllTest {
        @Test
        void itShouldGetAll() throws Exception {
            //Given
            //... return all warehouses
            WarehouseDto warehouse1 = new WarehouseDto();
            WarehouseDto warehouse2 = new WarehouseDto();
            given(warehouseService.getAll(any()))
                    .willReturn(new PageImpl<>(List.of(warehouse1, warehouse2)));
            //When
            ResultActions result = mockMvc.perform(get("/warehouses")
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.size").value(2));
        }
    }

    @Nested
    class GetOrderedByDistanceFromPostcodeTest {
        @Test
        void itShouldReturnWarehouses() throws Exception {
            //Given
            //... return all warehouses
            WarehouseDto warehouse1 = new WarehouseDto();
            WarehouseDto warehouse2 = new WarehouseDto();
            given(warehouseService.getOrderedByDistanceFromPostcode(POSTCODE))
                    .willReturn(List.of(warehouse1, warehouse2));
            //When
            ResultActions result = mockMvc.perform(get("/warehouses//{postcode}/ordered-by-distance", POSTCODE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2));
        }
    }

    @Nested
    class GetAvailableByWarehouseUuidTest {
        @Test
        void itShouldReturnedAvailableRoomsByWarehouseUUID() throws Exception {
            //Given
            // ... return two rooms
            StorageRoomDto room1 = new StorageRoomDto();
            StorageRoomDto room2 = new StorageRoomDto();
            given(storageRoomService.getAvailableByWarehouseUuid(eq(WAREHOUSE_UUID), any()))
                    .willReturn(new PageImpl<>(List.of(room1, room2)));
            //When
            ResultActions result = mockMvc.perform(get("/warehouses//{uuid}/available-rooms", WAREHOUSE_UUID)
                    .contentType(MediaType.APPLICATION_JSON));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.size").value(2));
        }
    }

    private <T> String toJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

}