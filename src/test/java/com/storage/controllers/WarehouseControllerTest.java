package com.storage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.Warehouse;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.requests.CreateWarehouseRequest;
import com.storage.repositories.UserRepository;
import com.storage.security.config.SecurityConfig;
import com.storage.security.tokens.TokensService;
import com.storage.service.StorageRoomService;
import com.storage.service.WarehouseService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
@Import(value = {SecurityConfig.class, TokensService.class})
class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StorageRoomService storageRoomService;
    @MockBean
    private WarehouseService warehouseService;
    @MockBean
    private UserRepository userRepository;

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
        @WithMockUser
        void itShouldCreateANewWarehouse() throws Exception {
            //Given
            CreateWarehouseRequest createWarehouseRequest = new CreateWarehouseRequest(NAME, CITY, POSTCODE, STREET, BigDecimal.TEN);
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
            given(warehouseService.create(createWarehouseRequest)).willReturn(warehouseDto);
            //When
            ResultActions result = mockMvc.perform(post("/api/v1/warehouses")
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
        @WithMockUser
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
            ResultActions result = mockMvc.perform(get("/api/v1/warehouses/{uuid}", WAREHOUSE_UUID)
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
        @WithMockUser
        void itShouldGetAll() throws Exception {
            //Given
            //... return all warehouses
            WarehouseDto warehouse1 = new WarehouseDto();
            WarehouseDto warehouse2 = new WarehouseDto();
            given(warehouseService.getAll(any()))
                    .willReturn(new PageImpl<>(List.of(warehouse1, warehouse2)));
            //When
            ResultActions result = mockMvc.perform(get("/api/v1/warehouses")
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.size").value(2));
        }
    }

    @Nested
    class GetOrderedByDistanceFromPostcodeTest {
        @Test
        @WithMockUser
        void itShouldReturnWarehouses() throws Exception {
            //Given
            //... return all warehouses
            WarehouseDto warehouse1 = new WarehouseDto();
            WarehouseDto warehouse2 = new WarehouseDto();
            given(warehouseService.getSortedByDistanceFromPostcode(POSTCODE))
                    .willReturn(List.of(warehouse1, warehouse2));
            //When
            ResultActions result = mockMvc.perform(get("/api/v1/warehouses/ordered-by-distance-from-postcode/{postcode}", POSTCODE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2));
        }
    }

    @Nested
    class GetAvailableByWarehouseUuidTest {
        @Test
        @WithMockUser
        void itShouldReturnedAvailableRoomsByWarehouseUUID() throws Exception {
            //Given
            // ... return two rooms
            StorageRoomDto room1 = new StorageRoomDto();
            StorageRoomDto room2 = new StorageRoomDto();
            given(storageRoomService.getAvailableByWarehouseUuid(eq(WAREHOUSE_UUID), any()))
                    .willReturn(new PageImpl<>(List.of(room1, room2)));
            //When
            ResultActions result = mockMvc.perform(get("/api/v1/warehouses/{uuid}/available-rooms", WAREHOUSE_UUID)
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