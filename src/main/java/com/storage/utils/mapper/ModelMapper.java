package com.storage.utils.mapper;

import com.storage.models.StorageRoom;
import com.storage.models.User;
import com.storage.models.Warehouse;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.UserDTO;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.requests.CreateUserRequest;

import java.util.List;
import java.util.stream.Collectors;

//TODO split all model mappers to separate classes and improve mapping. (ModelMapper)
public interface ModelMapper {

    static UserDTO fromUserToUserDto(User user) {
        return user == null ? null : UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .role(user.getRole())
                .build();
    }

    static User fromUserDtoToUser(CreateUserRequest createUserRequest) {
        return createUserRequest == null ? null : User.builder()
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .email(createUserRequest.getEmail())
                .gender(createUserRequest.getGender())
                .role(createUserRequest.getRole())
                .build();
    }

    static WarehouseDto fromWarehouseToWarehouseDto(Warehouse warehouse) {
        return warehouse == null ? null : WarehouseDto.builder()
                .uuid(warehouse.getUuid())
                .name(warehouse.getName())
                .city(warehouse.getCity())
                .postcode(warehouse.getPostcode())
                .street(warehouse.getStreet())
                .lat(warehouse.getLat())
                .lng(warehouse.getLng())
                .build();
    }

    static Warehouse fromWarehouseDtoToWarehouse(WarehouseDto warehouseDto) {
        return warehouseDto == null ? null : Warehouse.builder()
                .name(warehouseDto.getName())
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
                .uuid(storageRoom.getUuid())
                .storageSize(storageRoom.getStorageSize())
                .status(storageRoom.getStatus())
                .build();
    }

    static StorageRoom fromStorageRoomDtoToStorageRoom(StorageRoomDto storageRoomDto) {
        return storageRoomDto == null ? null : StorageRoom.builder()
                .storageSize(storageRoomDto.getStorageSize())
                .build();
    }

}
