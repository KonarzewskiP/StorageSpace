package com.storage.service;

import com.storage.models.StorageRoom;
import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.repositories.StorageRoomRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class StorageRoomServiceTest {
    private final String UUID = "storage-uuid";
    @InjectMocks
    private StorageRoomService underTest;
    @Mock
    private StorageRoomRepository storageRoomRepository;

    @Captor
    private ArgumentCaptor<StorageRoom> storageRoomArgumentCaptor;

    @Nested
    class UpdateTest {

        @Test
        void itShouldUpdate() {
            //Given

            StorageRoomStatus newStatus = StorageRoomStatus.MAINTENANCE;
            StorageSize newSize = StorageSize.LARGE_GARDEN_SHED;
            StorageUpdateRequest request = new StorageUpdateRequest(newSize, newStatus);
            //... return storage room from the DB
            StorageRoom storageRoom = new StorageRoom(StorageSize.TELEPHONE_BOX, StorageRoomStatus.AVAILABLE, 1L);
            given(storageRoomRepository.findByUuidForUpdate(UUID)).willReturn(Optional.of(storageRoom));

            //When
            underTest.update(UUID, request);
            //Then
            then(storageRoomRepository).should(times(1)).save(storageRoomArgumentCaptor.capture());

            StorageRoom result = storageRoomArgumentCaptor.getValue();

            assertAll(
                    () -> assertThat(result.getStatus()).isEqualTo(newStatus),
                    () -> assertThat(result.getStorageSize()).isEqualTo(newSize)
            );
        }

        @Test
        void itShouldUpdateOnlyStatus() {
            //Given

            StorageRoomStatus newStatus = StorageRoomStatus.MAINTENANCE;
            StorageSize size = StorageSize.LARGE_GARDEN_SHED;
            StorageUpdateRequest request = new StorageUpdateRequest(null, newStatus);
            //... return storage room from the DB
            StorageRoom storageRoom = new StorageRoom(size, StorageRoomStatus.AVAILABLE, 1L);
            given(storageRoomRepository.findByUuidForUpdate(UUID)).willReturn(Optional.of(storageRoom));

            //When
            underTest.update(UUID, request);
            //Then
            then(storageRoomRepository).should(times(1)).save(storageRoomArgumentCaptor.capture());

            StorageRoom result = storageRoomArgumentCaptor.getValue();

            assertAll(
                    () -> assertThat(result.getStatus()).isEqualTo(newStatus),
                    () -> assertThat(result.getStorageSize()).isEqualTo(storageRoom.getStorageSize())
            );
        }
    }
}

















