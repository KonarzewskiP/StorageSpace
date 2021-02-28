package com.storage.model.mapper;

import com.storage.model.User;
import com.storage.model.StorageRoom;
import com.storage.model.Warehouse;
import com.storage.model.dto.UserDto;
import com.storage.model.dto.StorageRoomDto;
import com.storage.model.dto.WarehouseDto;

public interface ModelMapper {

    static UserDto fromUserToUserDto(User user) {
        return user == null ? null : UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .build();
    }

    static User fromUserDtoToUser(UserDto userDto) {
        return userDto == null ? null : User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .gender(userDto.getGender())
                .build();
    }

    static WarehouseDto fromWarehouseToWarehouseDto(Warehouse warehouse) {
        return warehouse == null ? null : WarehouseDto.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .city(warehouse.getCity())
                .street(warehouse.getStreet())
                .postCode(warehouse.getPostCode())
                .build();
    }

    static Warehouse fromWarehouseDtoToWarehouse(WarehouseDto warehouseDto) {
        return warehouseDto == null ? null : Warehouse.builder()
                .id(warehouseDto.getId())
                .name(warehouseDto.getName())
                .city(warehouseDto.getCity())
                .street(warehouseDto.getStreet())
                .postCode(warehouseDto.getPostCode())
                .build();
    }

    static StorageRoomDto fromStorageRoomToStorageRoomDto(StorageRoom storageRoom) {
        return storageRoom == null ? null : StorageRoomDto.builder()
                .id(storageRoom.getId())
                .size(storageRoom.getSize())
                .reserved(storageRoom.isReserved())
                .startDate(storageRoom.getStartDate())
                .endDate(storageRoom.getEndDate())
                .build();
    }

    static StorageRoom fromStorageRoomDtoToStorageRoom(StorageRoomDto storageRoomDto) {
        return storageRoomDto == null ? null : StorageRoom.builder()
                .id(storageRoomDto.getId())
                .size(storageRoomDto.getSize())
                .reserved(storageRoomDto.getReserved())
                .startDate(storageRoomDto.getStartDate())
                .endDate(storageRoomDto.getEndDate())
                .build();
    }

}
