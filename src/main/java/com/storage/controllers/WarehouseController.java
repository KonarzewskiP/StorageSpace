package com.storage.controllers;

import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.mapper.ModelMapper;
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
    public ResponseEntity<WarehouseDto> createWarehouse(@RequestBody WarehouseDto warehouseDto) {
        log.info("Enter WarehouseController -> addWarehouse() with: " + warehouseDto);
        return new ResponseEntity<>(warehouseService.addWarehouse(warehouseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<WarehouseDto> getWarehouseByUuid(@PathVariable String uuid) {
        log.info("Enter WarehouseController -> getWarehouseByUuid() [UUID:{}] ", uuid);
        var warehouse = warehouseService.findByUuid(uuid);
        var warehouseDto = ModelMapper.fromWarehouseToWarehouseDto(warehouse);
        return new ResponseEntity<>(warehouseDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseDto>> getAllWarehouses() {
        log.info("Enter WarehouseController -> getAllWarehouses()");
        return new ResponseEntity<>(warehouseService.getAllWarehouses(), HttpStatus.OK);
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<List<StorageRoomDto>> getNotReservedStorageRoomsByWarehouseUuid(@PathVariable Long id) {
        log.info("Enter WarehouseController -> getNotReservedStorageRoomsByWarehouseId() with: " + id);
        return new ResponseEntity<>(warehouseService.getNotReservedStorageRoomsByWarehouseId(id), HttpStatus.OK);
    }

    /**
     * Search for nearest Warehouses according to given postcode.
     *
     * @param postcode
     * @return ResponseEntity with a <code>List<WarehouseDto></code> of ordered warehouses.
     * @author Pawel Konarzewski
     */
    @GetMapping("/{postcode}/nearest")
    public ResponseEntity<List<WarehouseDto>> getNearestFromPostcode(@PathVariable String postcode) {
        log.info("Enter PostcodeController -> getNearestWarehouses() with: " + postcode);
        return new ResponseEntity<>(warehouseService.getOrderedByDistanceFromPostcode(postcode), HttpStatus.OK);
    }


}
