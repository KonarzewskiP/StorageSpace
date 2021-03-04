package com.storage.controller;

import com.storage.model.dto.StorageRoomDto;
import com.storage.model.dto.WarehouseDto;
import com.storage.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseDto> addWarehouse(@RequestBody WarehouseDto warehouseDto) {
        log.info("Enter WarehouseController -> addWarehouse() with: " + warehouseDto);
        return new ResponseEntity<>(warehouseService.addWarehouse(warehouseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable Long id) {
        log.info("Enter WarehouseController -> getWarehouseById() with: " + id);
        return new ResponseEntity<>(warehouseService.getWarehouseById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseDto>> getAllWarehouses() {
        log.info("Enter WarehouseController -> getAllWarehouses()");
        return new ResponseEntity<>(warehouseService.getAllWarehouses(), HttpStatus.OK);
    }

    @GetMapping("/available/{id}")
    public ResponseEntity<List<StorageRoomDto>> getNotReservedStorageRoomsByWarehouseId(@PathVariable Long id) {
        log.info("Enter WarehouseController -> getNotReservedStorageRoomsByWarehouseId() with: " + id);
        return new ResponseEntity<>(warehouseService.getNotReservedStorageRoomsByWarehouseId(id), HttpStatus.OK);
    }

}
