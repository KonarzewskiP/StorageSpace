package com.storage.service;

import com.storage.builders.Fixtures;
import com.storage.exceptions.ResourceNotFoundException;
import com.storage.exceptions.StorageRoomException;
import com.storage.models.StorageRoom;
import com.storage.models.dto.StorageRoomDto;
import com.storage.repositories.StorageRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.storage.builders.Fixtures.createStorageRoom;
import static com.storage.builders.Fixtures.createStorageRoomDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class StorageRoomServiceTest {

    @InjectMocks
    private StorageRoomService service;

    @Mock
    private StorageRoomRepository storageRoomRepository;

    @Test
    @DisplayName("should update storageRoom")
    void shouldUpdateStorageRoom() {
        //given
        var storageRoom = createStorageRoom();
        when(storageRoomRepository.save(any(StorageRoom.class))).thenReturn(storageRoom);
        when(storageRoomRepository.findById(any(Long.class))).thenReturn(Optional.of(storageRoom));
        var storageRoomDto = Fixtures.createStorageRoomDto();
        //when
        var result = service.updateStorageRoom(storageRoomDto);
        //then
        assertThat(result.getId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("should throw StorageRoomException when storageRoomDto is null ")
    void shouldThrowStorageRoomExceptionWhenStorageRoomDtoIsNull() {
        //given
        StorageRoomDto storageRoomDto = null;
        //when
        var thrown = catchThrowable(() -> service.updateStorageRoom(storageRoomDto));
        //then
        assertThat(thrown)
                .isInstanceOf(StorageRoomException.class)
                .hasMessageContaining("Invalid StorageRoomDto!");
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException when can not find id of storageRoomDto")
    void shouldThrowResourceNotFoundExceptionWhenCanNotFindIdOfStorageRoomDto() {
        //given
        var storageRoomDto = createStorageRoomDto();
        storageRoomDto.setId(999L);
        //when
        var thrown = catchThrowable(() -> service.updateStorageRoom(storageRoomDto));
        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("StorageRoom not found with id: 999");
    }

    @Test
    @DisplayName("should throw StorageRoomException when storageRoomDto is invalid")
    void shouldThrowStorageRoomExceptionWhenStorageRoomDtoIsInvalid() {
        //given
        var storageRoomDto = createStorageRoomDto();
        storageRoomDto.setReserved(null);
        //when
        var thrown = catchThrowable(() -> service.updateStorageRoom(storageRoomDto));
        //then
        assertThat(thrown)
                .isInstanceOf(StorageRoomException.class)
                .hasMessageContaining("Invalid StorageRoomDto!")
                .hasMessageContaining("Can not be null");
    }

    @Test
    @DisplayName("should find storageRoom by id")
    void shouldFindStorageRoomById() {
        //given
        var storageRoom = createStorageRoom();
        when(storageRoomRepository.findById(anyLong())).thenReturn(Optional.of(storageRoom));
        //when
        var result = service.findStorageRoomById(999L);
        //then
        assertThat(result.getId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException when can not find storageRoom by id")
    void shouldThrowResourceNotFoundExceptionWhenCanNotFindStorageRoomById() {
        //given
        var id = 999L;
        //when
        var thrown = catchThrowable(() -> service.findStorageRoomById(id));
        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("StorageRoom not found with id: 999");
    }

    @Test
    @DisplayName("should return ResourceNotFoundException when id is null")
    void shouldReturnResourceNotFoundExceptionWhenIdIsNull() {
        //given
        Long id = null;
        //when
        var thrown = catchThrowable(() -> service.findStorageRoomById(id));
        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("StorageRoom not found with id: null");
    }

}

















