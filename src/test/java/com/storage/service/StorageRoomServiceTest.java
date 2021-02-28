package com.storage.service;

import com.storage.builders.MockDataForTest;
import com.storage.model.StorageRoom;
import com.storage.repository.StorageRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class StorageRoomServiceTest {

    @InjectMocks
    private StorageRoomService service;

    @Mock
    private StorageRoomRepository storageRoomRepository;

    @Test
    @DisplayName("should add storageRoom to repository")
    void shouldAddStorageRoomToRepository() {
        //given
        var storageRoom = MockDataForTest.createStorageRoom();
        when(storageRoomRepository.save(any(StorageRoom.class))).thenReturn(storageRoom);
        var storageRoomDto = MockDataForTest.createStorageRoomDto();
        //when
        var result = service.addStorageRoom(storageRoomDto);
        //then
        assertThat(result.getId()).isEqualTo(3L);
    }
}
