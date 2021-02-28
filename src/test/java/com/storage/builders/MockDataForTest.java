package com.storage.builders;

import com.storage.model.StorageRoom;
import com.storage.model.User;
import com.storage.model.Warehouse;
import com.storage.model.dto.StorageRoomDto;
import com.storage.model.dto.UserDto;
import com.storage.model.dto.WarehouseDto;
import com.storage.model.enums.Gender;
import com.storage.model.enums.Role;
import com.storage.model.enums.Size;

import java.time.LocalDateTime;

public interface MockDataForTest {

    LocalDateTime START_DATE = LocalDateTime.of(2021,3,13,12, 0,0);
    LocalDateTime END_DATE = LocalDateTime.of(2021,4,11,12, 0,0);

    static User createUser(){
        return User.builder()
                .id(1L)
                .firstName("Veronica")
                .lastName("Jobs")
                .gender(Gender.FEMALE)

                .build();
    }

    static UserDto createUserDto(){
        return UserDto.builder()
                .id(10L)
                .firstName("John")
                .lastName("Orange")
                .email("storageRooms@fakeEmail.com")
                .role(Role.CUSTOMER)
                .gender(Gender.MALE)
                .build();
    }

    static Warehouse createWarehouse(){
        return Warehouse.builder()
                .id(2L)
                .name("Big Yellow")
                .city("London")
                .street("289 Kennington Ln")
                .postCode("SE11 5QY")
                .build();
    }

    static WarehouseDto createWarehouseDto(){
        return WarehouseDto.builder()
                .id(2L)
                .name("Big Yellow")
                .city("London")
                .street("289 Kennington Ln")
                .postCode("SE11 5QY")
                .build();
    }

    static StorageRoom createStorageRoom(){
        return StorageRoom.builder()
                .id(3L)
                .size(Size.GARDEN_SHED)
                .reserved(true)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();
    }

    static StorageRoomDto createStorageRoomDto(){
        return StorageRoomDto.builder()
                .id(30L)
                .size(Size.GARDEN_SHED)
                .reserved(false)
                .startDate(null)
                .endDate(null)
                .build();
    }
}
