package com.storage.builders;

import com.storage.model.User;
import com.storage.model.Warehouse;
import com.storage.model.dto.UserDto;
import com.storage.model.dto.WarehouseDto;
import com.storage.model.enums.Gender;
import com.storage.model.enums.Role;

public interface MockDataForTest {

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
}
