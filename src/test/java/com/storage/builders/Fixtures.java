package com.storage.builders;

import com.storage.models.*;
import com.storage.models.dto.AddressDto;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.UserDto;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.dto.externals.postcode.Result;
import com.storage.models.enums.*;
import com.storage.models.dto.externals.postcode.PostcodeResponse;
import com.storage.models.dto.externals.postcode.PostcodeValidationResponse;

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
                .id(1L)
                .firstName("Veronica")
                .lastName("Jobs")
                .gender(Gender.FEMALE)
                .build();
    }

    public static UserDto createUserDto() {
        return UserDto.builder()
                .id(10L)
                .firstName("John")
                .lastName("Orange")
                .email("storageRooms@fakeEmail.com")
                .role(Role.CUSTOMER)
                .gender(Gender.MALE)
                .build();
    }

    public static Warehouse createWarehouse() {
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

    public static WarehouseDto createWarehouseDto() {
        return WarehouseDto.builder()
                .id(2L)
                .name("Big Yellow")
                .address(AddressDto.builder()
                        .city("London")
                        .street("289 Kennington Ln")
                        .postcode("SE11 5QY")
                        .build())
                .build();
    }

    public static StorageRoom createStorageRoom() {
        return StorageRoom.builder()
                .id(3L)
                .size(Size.GARDEN_SHED)
                .reserved(true)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();
    }

    public static StorageRoomDto createStorageRoomDto() {
        return StorageRoomDto.builder()
                .id(30L)
                .size(Size.GARDEN_SHED)
                .reserved(false)
                .startDate(null)
                .endDate(null)
                .build();
    }

    public static List<StorageRoom> createStorageRoomsList() {
        AtomicLong index = new AtomicLong(100L);
        return Arrays.stream(Size.values())
                .map(size -> StorageRoom.builder()
                        .id(index.getAndIncrement())
                        .size(size)
                        .build())
                .collect(Collectors.toList());
    }

    public static List<StorageRoom> createIncrementalNumberOfStorageRoomsList() {
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

    public static Quote createQuote() {
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

    public static Map<String, List<String>> createMapForBulkPostcodesRequest() {
        return Map.of("postcodes", List.of("SE11 5QY", "SW19 3BE", "TW9 2JX"));
    }

    public static PostcodeResponse createPostcodeResponse() {
        var postcodeResult = Result.builder()
                .postcode("SW178EF")
                .latitude(51.431047)
                .longitude(-0.155261)
                .build();

        return PostcodeResponse.builder()
                .status(200)
                .result(postcodeResult)
                .build();
    }

    public static PostcodeValidationResponse createPositivePostcodeValidationResponse(){
        return PostcodeValidationResponse.builder()
                .status(200)
                .result(true)
                .build();

    }
    public static PostcodeValidationResponse createNegativePostcodeValidationResponse(){
        return PostcodeValidationResponse.builder()
                .status(200)
                .result(false)
                .build();

    }

    public static AddressDto createAddressDto(){
        return AddressDto.builder()
                .city("London")
                .postcode("NW1 9PA")
                .street("Camden St")
                .build();
    }

    public static Address createAddress(){
        return Address.builder()
                .id(1L)
                .city("London")
                .postcode("NW1 9PA")
                .street("Camden St")
                .build();
    }
}
