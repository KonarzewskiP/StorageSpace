package com.storage.models.mapper;

import com.storage.models.Address;
import com.storage.models.StorageRoom;
import com.storage.models.User;
import com.storage.models.Warehouse;
import com.storage.models.dto.AddressDto;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.UserDto;
import com.storage.models.dto.WarehouseDto;

import java.util.List;
import java.util.stream.Collectors;

//TODO split all model mappers to separate classes and improve mapping. (ModelMapper)
public interface ModelMapper {

    static UserDto fromUserToUserDto(User user) {
        return user == null ? null : UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .role(user.getRole())
                .build();
    }

    static User fromUserDtoToUser(UserDto userDto) {
        return userDto == null ? null : User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .gender(userDto.getGender())
                .role(userDto.getRole())
                .build();
    }

    static WarehouseDto fromWarehouseToWarehouseDto(Warehouse warehouse) {
        return warehouse == null ? null : WarehouseDto.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .address(fromAddressToAddressDto(warehouse.getAddress()))
                .build();
    }

    static Warehouse fromWarehouseDtoToWarehouse(WarehouseDto warehouseDto) {
        return warehouseDto == null ? null : Warehouse.builder()
//                .id(warehouseDto.getId())
                .name(warehouseDto.getName())
                .address(fromAddressDtoToAddress(warehouseDto.getAddress()))
                .build();
    }

    static AddressDto fromAddressToAddressDto(Address address) {
        return address == null ? null : AddressDto.builder()
                .postcode(address.getPostcode())
                .city(address.getCity())
                .street(address.getStreet())
                .build();
    }

    static Address fromAddressDtoToAddress(AddressDto addressDto) {
        return addressDto == null ? null : Address.builder()
                .postcode(addressDto.getPostcode())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
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
                .storageSize(storageRoom.getStorageSize())
                .reserved(storageRoom.isReserved())
                .startDate(storageRoom.getStartDate())
                .endDate(storageRoom.getEndDate())
                .build();
    }

    static StorageRoom fromStorageRoomDtoToStorageRoom(StorageRoomDto storageRoomDto) {
        return storageRoomDto == null ? null : StorageRoom.builder()
                .storageSize(storageRoomDto.getStorageSize())
                .reserved(storageRoomDto.getReserved())
                .startDate(storageRoomDto.getStartDate())
                .endDate(storageRoomDto.getEndDate())
                .build();
    }

}
