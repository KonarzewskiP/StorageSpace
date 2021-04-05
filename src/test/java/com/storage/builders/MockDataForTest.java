package com.storage.builders;

import com.storage.models.*;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.UserDto;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.enums.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface MockDataForTest {

    LocalDate START_DATE = LocalDate.of(2021, 3, 13);
    LocalDate END_DATE = LocalDate.of(2021, 4, 11);

    static User createUser() {
        return User.builder()
                .id(1L)
                .firstName("Veronica")
                .lastName("Jobs")
                .gender(Gender.FEMALE)

                .build();
    }

    static UserDto createUserDto() {
        return UserDto.builder()
                .id(10L)
                .firstName("John")
                .lastName("Orange")
                .email("storageRooms@fakeEmail.com")
                .role(Role.CUSTOMER)
                .gender(Gender.MALE)
                .build();
    }

    static Warehouse createWarehouse() {
        return Warehouse.builder()
                .id(2L)
                .name("Big Yellow")
                .address(Address.builder()
                        .city("London")
                        .street("289 Kennington Ln")
                        .postcode("SE11 5QY")
                        .build())
                .storageRooms(createStorageRoomsList())
                .build();
    }

    static WarehouseDto createWarehouseDto() {
        return WarehouseDto.builder()
                .id(2L)
                .name("Big Yellow")
                .address(Address.builder()
                        .city("London")
                        .street("289 Kennington Ln")
                        .postcode("SE11 5QY")
                        .build())
                .build();
    }

    static StorageRoom createStorageRoom() {
        return StorageRoom.builder()
                .id(3L)
                .size(Size.GARDEN_SHED)
                .reserved(true)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();
    }

    static StorageRoomDto createStorageRoomDto() {
        return StorageRoomDto.builder()
                .id(30L)
                .size(Size.GARDEN_SHED)
                .reserved(false)
                .startDate(null)
                .endDate(null)
                .build();
    }

    static List<StorageRoom> createStorageRoomsList() {
        AtomicLong index = new AtomicLong(100L);
        return Arrays.stream(Size.values())
                .map(size -> StorageRoom.builder()
                        .id(index.getAndIncrement())
                        .size(size)
                        .build())
                .collect(Collectors.toList());
    }

    static List<StorageRoom> createIncrementalNumberOfStorageRoomsList() {
        AtomicLong index = new AtomicLong(100L);
        AtomicInteger count = new AtomicInteger(1);
        return Arrays.stream(Size.values())
                .flatMap(size -> IntStream.range(0, count.getAndIncrement())
                        .mapToObj(i -> StorageRoom.builder()
                                .id(index.getAndIncrement())
                                .size(size)
                                .build()
                        )
                )
                .collect(Collectors.toList());
    }

    static Quote createQuote() {
        return Quote.builder()
                .firstName("John")
                .surname("Bravo")
                .email("johnBravo@gmail.com")
                .postcode("SE11 5QY")
                .warehouseName("Big Yellow")
                .size(Size.THREE_SINGLE_GARAGES)
                .type(TypeOfStorage.HOME)
                .startDate(LocalDate.now().plusDays(1))
                .duration(StorageDuration.EIGHT_TWELVE_WEEKS)
                .build();
    }

    static Map<String, List<String>> createMapForBulkPostcodesRequest() {
        return Map.of("postcodes", List.of("SE11 5QY", "SW19 3BE", "TW9 2JX"));
    }
}
