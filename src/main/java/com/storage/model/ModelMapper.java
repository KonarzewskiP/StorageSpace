package com.storage.model;

import com.storage.model.dto.DirectorDto;
import com.storage.model.dto.StorageRoomDto;
import com.storage.model.dto.WarehouseDto;

public interface ModelMapper {

    static DirectorDto fromDirectorToDirectorDto(Director director) {
        return director == null ? null : DirectorDto.builder()
                .id(director.getId())
                .firstName(director.getFirstName())
                .lastName(director.getLastName())
                .gender(director.getGender())
                .isValid(director.getIsValid())
                .warehouses(director.getWarehouses())
                .build();
    }

    static Director fromDirectorDtoToDirector(DirectorDto directorDto) {
        return directorDto == null ? null : Director.builder()
                .id(directorDto.getId())
                .firstName(directorDto.getFirstName())
                .lastName(directorDto.getLastName())
                .gender(directorDto.getGender())
                .isValid(directorDto.getIsValid())
                .warehouses(directorDto.getWarehouses())
                .build();
    }

    static WarehouseDto fromWarehouseToWarehouseDto(Warehouse warehouse) {
        return warehouse == null ? null : WarehouseDto.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .city(warehouse.getCity())
                .street(warehouse.getStreet())
                .postCode(warehouse.getPostCode())
                .director(warehouse.getDirector())
                .build();
    }

    static Warehouse fromWarehouseDtoToWarehouse(WarehouseDto warehouseDto) {
        return warehouseDto == null ? null : Warehouse.builder()
                .id(warehouseDto.getId())
                .name(warehouseDto.getName())
                .city(warehouseDto.getCity())
                .street(warehouseDto.getStreet())
                .postCode(warehouseDto.getPostCode())
                .director(warehouseDto.getDirector())
                .build();
    }

    static StorageRoomDto fromStorageRoomToStorageRoomDto(StorageRoom storageRoom) {
        return storageRoom == null ? null : StorageRoomDto.builder()
                .id(storageRoom.getId())
                .sizeM2(storageRoom.getSizeM2())
                .reserved(storageRoom.isReserved())
                .startDate(storageRoom.getStartDate())
                .endDate(storageRoom.getEndDate())
                .build();
    }

    static StorageRoom fromStorageRoomDtoToStorageRoom(StorageRoomDto storageRoomDto) {
        return storageRoomDto == null ? null : StorageRoom.builder()
                .id(storageRoomDto.getId())
                .sizeM2(storageRoomDto.getSizeM2())
                .reserved(storageRoomDto.isReserved())
                .startDate(storageRoomDto.getStartDate())
                .endDate(storageRoomDto.getEndDate())
                .build();
    }

}
