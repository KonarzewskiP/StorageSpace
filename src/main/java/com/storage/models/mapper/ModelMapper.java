package com.storage.models.mapper;

import com.storage.models.User;
import com.storage.models.StorageRoom;
import com.storage.models.Warehouse;
import com.storage.models.dto.UserDto;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.WarehouseDto;

import java.util.List;
import java.util.stream.Collectors;

public interface ModelMapper {

    static UserDto fromUserToUserDto(User user) {
        return user == null ? null : UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }

    static User fromUserDtoToUser(UserDto userDto) {
        return userDto == null ? null : User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
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

    static List<WarehouseDto> fromWarehouseListToWarehouseDtoList(List<Warehouse> warehouses) {
        return warehouses == null ? null : warehouses
                .stream()
                .map(ModelMapper::fromWarehouseToWarehouseDto)
                .collect(Collectors.toList());
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
