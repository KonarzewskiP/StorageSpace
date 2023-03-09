package com.storage.builders;

import com.storage.models.StorageRoom;
import com.storage.models.User;
import com.storage.models.Warehouse;
import com.storage.models.businessObject.Quote;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import com.storage.models.enums.Gender;
import com.storage.models.enums.Role;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import com.storage.models.requests.CreateUserRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Fixtures {

    private Fixtures() {
    }

    public static LocalDate START_DATE = LocalDate.of(2021, 3, 13);
    public static LocalDate END_DATE = LocalDate.of(2021, 4, 11);

    public static User createUser() {
        return User.builder()
                .firstName("Veronica")
                .lastName("Jobs")
                .gender(Gender.FEMALE)
                .build();
    }

    public static CreateUserRequest createUserDto() {
        return CreateUserRequest.builder()
                .firstName("John")
                .lastName("Orange")
                .email("storageRooms@fakeEmail.com")
                .role(Role.CUSTOMER)
                .gender(Gender.MALE)
                .build();
    }

    public static Warehouse createWarehouse() {
        return Warehouse.builder()
                .name("Big Yellow")
                .city("London")
                .street("289 Kennington Ln")
                .postcode("SE11 5QY")
                .build();
    }

    public static WarehouseDto createWarehouseDto() {
        return WarehouseDto.builder()
                .name("Big Yellow")
                .city("London")
                .street("289 Kennington Ln")
                .postcode("SE11 5QY")
                .build();
    }

    public static StorageRoom createStorageRoom() {
        return StorageRoom.builder()
                .storageSize(StorageSize.GARDEN_SHED)
                .build();
    }

    public static StorageRoomDto createStorageRoomDto() {
        return StorageRoomDto.builder()
                .storageSize(StorageSize.GARDEN_SHED)
                .build();
    }

    public static List<StorageRoom> createStorageRoomsList() {
        AtomicLong index = new AtomicLong(100L);
        return Arrays.stream(StorageSize.values())
                .map(size -> StorageRoom.builder()
//                        .id(index.getAndIncrement())
                        .storageSize(size)
                        .build())
                .collect(Collectors.toList());
    }

    public static List<StorageRoom> createIncrementalNumberOfStorageRoomsList() {
        AtomicLong index = new AtomicLong(100L);
        AtomicInteger count = new AtomicInteger(1);
        return Arrays.stream(StorageSize.values())
                .flatMap(size -> IntStream.range(0, count.getAndIncrement())
                                .mapToObj(i -> StorageRoom.builder()
//                                .id(index.getAndIncrement())
                                                .storageSize(size)
                                                .build()
                                )
                )
                .collect(Collectors.toList());
    }

    public static Quote createQuote() {
        return Quote.builder()
                .firstName("John")
                .surname("Bravo")
                .email("johnBravo@gmail.com")
                .postcode("SE11 5QY")
                .warehouseName("Big Yellow")
                .storageSize(StorageSize.THREE_SINGLE_GARAGES)
                .startDate(LocalDate.now().plusDays(1))
                .duration(StorageDuration.UP_TO_6_MONTHS)
                .build();
    }

    public static Map<String, List<String>> createMapForBulkPostcodesRequest() {
        return Map.of("postcodes", List.of("SE11 5QY", "SW19 3BE", "TW9 2JX"));
    }

    public static PostcodeValidateDTO createPositivePostcodeValidationResponse() {
        return PostcodeValidateDTO.builder()
                .status(200)
                .result(true)
                .build();

    }
    public static PostcodeValidateDTO createNegativePostcodeValidationResponse() {
        return PostcodeValidateDTO.builder()
                .status(200)
                .result(false)
                .build();

    }
}
