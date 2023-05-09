package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.models.StorageRoom;
import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import com.storage.models.requests.CreateStorageRoomsRequest;
import com.storage.models.requests.SingleStorageRoomRequest;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.repositories.StorageRoomRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @Mock
    private WarehouseService warehouseService;
    @Captor
    private ArgumentCaptor<StorageRoom> storageRoomArgumentCaptor;
    @Captor
    private ArgumentCaptor<List<StorageRoom>> listStorageRoomsArgumentCaptor;

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

    @Nested
    class CreateTest {

        @Test
        void itShouldCreateOneStorageRoom() {
            //Given
            String warehouseUuid = "warehouse-uuid";
            SingleStorageRoomRequest singleRoom = SingleStorageRoomRequest.builder()
                    .storageSize(StorageSize.TELEPHONE_BOX)
                    .status(StorageRoomStatus.AVAILABLE)
                    .build();
            CreateStorageRoomsRequest roomsRequest = CreateStorageRoomsRequest.builder()
                    .warehouseUuid(warehouseUuid)
                    .storageRoomList(List.of(singleRoom))
                    .build();

            //... returns valid Warehouse ID
            Long warehouseID = 1L;
            given(warehouseService.findIdByUuid(warehouseUuid)).willReturn(warehouseID);
            //When
            underTest.create(roomsRequest);
            //Then
            then(storageRoomRepository).should(times(1)).saveAll(listStorageRoomsArgumentCaptor.capture());
            List<StorageRoom> savedStorageRooms = listStorageRoomsArgumentCaptor.getValue();

            assertAll(
                    () -> assertThat(savedStorageRooms.size()).isEqualTo(1),
                    () -> assertThat(savedStorageRooms.get(0).getStorageSize()).isEqualTo(singleRoom.storageSize()),
                    () -> assertThat(savedStorageRooms.get(0).getStatus()).isEqualTo(singleRoom.status()),
                    () -> assertThat(savedStorageRooms.get(0).getWarehouseId()).isEqualTo(warehouseID)
            );
        }

        @Test
        void itShouldCreateMultipleStorageRooms() {
            //Given
            String warehouseUuid = "warehouse-uuid";
            SingleStorageRoomRequest singleRoom1 = SingleStorageRoomRequest.builder()
                    .storageSize(StorageSize.TELEPHONE_BOX)
                    .status(StorageRoomStatus.AVAILABLE)
                    .build();
            SingleStorageRoomRequest singleRoom2 = SingleStorageRoomRequest.builder()
                    .storageSize(StorageSize.TELEPHONE_BOX)
                    .status(StorageRoomStatus.AVAILABLE)
                    .build();
            SingleStorageRoomRequest singleRoom3 = SingleStorageRoomRequest.builder()
                    .storageSize(StorageSize.TELEPHONE_BOX)
                    .status(StorageRoomStatus.AVAILABLE)
                    .build();
            CreateStorageRoomsRequest roomsRequest = CreateStorageRoomsRequest.builder()
                    .warehouseUuid(warehouseUuid)
                    .storageRoomList(List.of(singleRoom1, singleRoom2, singleRoom3))
                    .build();

            //... returns valid Warehouse ID
            Long warehouseID = 1L;
            given(warehouseService.findIdByUuid(warehouseUuid)).willReturn(warehouseID);
            //When
            underTest.create(roomsRequest);
            //Then
            then(storageRoomRepository).should(times(1)).saveAll(listStorageRoomsArgumentCaptor.capture());
            List<StorageRoom> savedStorageRooms = listStorageRoomsArgumentCaptor.getValue();

            assertAll(
                    () -> assertThat(savedStorageRooms.size()).isEqualTo(3),
                    () -> assertThat(savedStorageRooms.get(0).getStorageSize()).isEqualTo(singleRoom1.storageSize()),
                    () -> assertThat(savedStorageRooms.get(0).getStatus()).isEqualTo(singleRoom1.status()),
                    () -> assertThat(savedStorageRooms.get(0).getWarehouseId()).isEqualTo(warehouseID),
                    () -> assertThat(savedStorageRooms.get(1).getStorageSize()).isEqualTo(singleRoom2.storageSize()),
                    () -> assertThat(savedStorageRooms.get(1).getStatus()).isEqualTo(singleRoom2.status()),
                    () -> assertThat(savedStorageRooms.get(1).getWarehouseId()).isEqualTo(warehouseID),
                    () -> assertThat(savedStorageRooms.get(2).getStorageSize()).isEqualTo(singleRoom3.storageSize()),
                    () -> assertThat(savedStorageRooms.get(2).getStatus()).isEqualTo(singleRoom3.status()),
                    () -> assertThat(savedStorageRooms.get(2).getWarehouseId()).isEqualTo(warehouseID)
            );
        }
    }

    @Nested
    class DeleteTest {

        @ParameterizedTest
        @NullAndEmptySource
        void itShouldThrowErrorWhenRequestListOfUuidsIsInvalid(List<String> uuids) {
            //Given
            //When
            //Then
            assertThatThrownBy(() -> underTest.delete(uuids))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining("List of uuids can not be null or empty!");
        }

        @Test
        void itShouldReturnNumberOfDeletedRooms() {
            //Given
            List<String> request = List.of("AAA", "BBB", "CCC");

            //... return 3 because 3 rooms were deleted
            int numberOfDeletedRooms = 3;
            given(storageRoomRepository.deleteAllByUuids(request)).willReturn(numberOfDeletedRooms);
            //When
            int result = underTest.delete(request);
            //Then
            assertThat(result).isEqualTo(numberOfDeletedRooms);
        }
    }
}

















