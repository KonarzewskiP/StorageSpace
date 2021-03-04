package com.storage.builders;

import com.storage.model.Quote;
import com.storage.model.StorageRoom;
import com.storage.model.User;
import com.storage.model.Warehouse;
import com.storage.model.dto.StorageRoomDto;
import com.storage.model.dto.UserDto;
import com.storage.model.dto.WarehouseDto;
import com.storage.model.enums.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface MockDataForTest {

    LocalDateTime START_DATE = LocalDateTime.of(2021, 3, 13, 12, 0, 0);
    LocalDateTime END_DATE = LocalDateTime.of(2021, 4, 11, 12, 0, 0);

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
                .city("London")
                .street("289 Kennington Ln")
                .postCode("SE11 5QY")
                .storageRooms(createStorageRoomsList())
                .build();
    }

    static WarehouseDto createWarehouseDto() {
        return WarehouseDto.builder()
                .id(2L)
                .name("Big Yellow")
                .city("London")
                .street("289 Kennington Ln")
                .postCode("SE11 5QY")
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

    static Quote createQuote(){
        return Quote.builder()
                .firstName("John")
                .surname("Bravo")
                .email("johnBravo@gmail.com")
                .postcode("SE11 5QY")
                .warehouseName("Big Yellow")
                .size(Size.THREE_SINGLE_GARAGES)
                .type(TypeOfAccount.HOME)
                .startDate(LocalDateTime.now())
                .duration(StorageDuration.EIGHT_TWELVE_WEEKS)
                .build();
    }
}
