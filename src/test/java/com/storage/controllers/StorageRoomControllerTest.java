package com.storage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.StorageRoom;
import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.service.StorageRoomService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    private static final String STORAGE_ROOM_UUID = "RANDOM-UUID";

    @Nested
    class UpdateTest {
        @Test
        void itShouldUpdateByUuid() throws Exception {
            //Given
            StorageUpdateRequest request = new StorageUpdateRequest();
            // ... storageRoom is successfully updated and returned by storageRoomService
            StorageRoom storageRoom = StorageRoom.builder()
                    .uuid(STORAGE_ROOM_UUID)
                    .storageSize(StorageSize.LARGE_GARDEN_SHED)
                    .status(StorageRoomStatus.AVAILABLE)
                    .build();
            given(storageRoomService.updateStorageRoom(STORAGE_ROOM_UUID, request)).willReturn(storageRoom);

            //When
            ResultActions result = mockMvc.perform(put("/storage-rooms/{uuid}", STORAGE_ROOM_UUID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(request)));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.uuid").value(STORAGE_ROOM_UUID))
                    .andExpect(jsonPath("$.storageSize").value(StorageSize.LARGE_GARDEN_SHED.toString()))
                    .andExpect(jsonPath("$.status").value(StorageRoomStatus.AVAILABLE.toString()));
        }
    }

    @Nested
    class FindByUuidTest {
        @Test
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
            ResultActions result = mockMvc.perform(get("/storage-rooms/{uuid}", STORAGE_ROOM_UUID)
                            .contentType(MediaType.APPLICATION_JSON));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.uuid").value(STORAGE_ROOM_UUID))
                    .andExpect(jsonPath("$.storageSize").value(StorageSize.LARGE_GARDEN_SHED.toString()))
                    .andExpect(jsonPath("$.status").value(StorageRoomStatus.AVAILABLE.toString()));
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












