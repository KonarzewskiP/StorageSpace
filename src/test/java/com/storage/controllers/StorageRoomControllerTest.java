package com.storage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.StorageRoom;
import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import com.storage.models.requests.CreateStorageRoomsRequest;
import com.storage.models.requests.SingleStorageRoomRequest;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.service.StorageRoomService;
import com.storage.utils.mapper.StorageRoomMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(StorageRoomController.class)
class StorageRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StorageRoomService storageRoomService;
    @SpyBean
    private StorageRoomMapper storageRoomMapper;

    private static final String STORAGE_ROOM_UUID = "RANDOM-UUID";

    @Nested
    class UpdateTest {
        @Test
        @WithMockUser
        void itShouldUpdateByUuid() throws Exception {
            //Given
            StorageUpdateRequest request = StorageUpdateRequest.builder()
                    .storageSize(StorageSize.LARGE_GARDEN_SHED)
                    .status(StorageRoomStatus.MAINTENANCE)
                    .build();
            // ... storageRoom is successfully updated and returned by storageRoomService
            StorageRoom storageRoom = StorageRoom.builder().build();
            given(storageRoomService.update(STORAGE_ROOM_UUID, request)).willReturn(storageRoom);
            //When
            ResultActions result = mockMvc.perform(put("/api/v1/storage-rooms/{uuid}", STORAGE_ROOM_UUID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(request)));
            //Then
            result.andExpect(status().isOk());
        }
    }

    @Nested
    class FindByUuidTest {
        @Test
        @WithMockUser
        void itShouldReturnStorageRoomByUuid() throws Exception {
            //Given
            // ... returned room
            StorageRoom storageRoom = StorageRoom.builder()
                    .uuid(STORAGE_ROOM_UUID)
                    .storageSize(StorageSize.LARGE_GARDEN_SHED)
                    .status(StorageRoomStatus.AVAILABLE)
                    .build();
            given(storageRoomService.findByUuid(STORAGE_ROOM_UUID)).willReturn(storageRoom);

            //When
            ResultActions result = mockMvc.perform(get("/api/v1/storage-rooms/{uuid}", STORAGE_ROOM_UUID)
                    .contentType(MediaType.APPLICATION_JSON));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.uuid").value(STORAGE_ROOM_UUID))
                    .andExpect(jsonPath("$.storageSize").value(StorageSize.LARGE_GARDEN_SHED.toString()))
                    .andExpect(jsonPath("$.status").value(StorageRoomStatus.AVAILABLE.toString()));
        }
    }

    @Nested
    class CreateTest {
        @Test
        @WithMockUser
        void itShouldCreate() throws Exception {
            //Given
            String warehouseUuid = "warehouse-uuid";
            SingleStorageRoomRequest roomRequest = SingleStorageRoomRequest.builder()
                    .status(StorageRoomStatus.MAINTENANCE)
                    .storageSize(StorageSize.TELEPHONE_BOX)
                    .build();
            CreateStorageRoomsRequest request = CreateStorageRoomsRequest.builder()
                    .warehouseUuid(warehouseUuid)
                    .storageRoomList(List.of(roomRequest))
                    .build();

            // after successfully created request storageRoomService return new rooms
            given(storageRoomService.create(request)).willReturn(List.of());
            //When
            ResultActions result = mockMvc.perform(post("/api/v1/storage-rooms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(request)));
            //Then
            result.andExpect(status().isCreated());
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












