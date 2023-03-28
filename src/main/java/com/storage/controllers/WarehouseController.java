package com.storage.controllers;

import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.requests.CreateWarehouseRequest;
import com.storage.service.StorageRoomService;
import com.storage.service.WarehouseService;
import com.storage.utils.annotation.ApiPageable;
import com.storage.utils.mapper.ModelMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final StorageRoomService storageRoomService;

    @PostMapping
    public ResponseEntity<WarehouseDto> create(@RequestBody @Valid CreateWarehouseRequest warehouseRequest) {
        log.info("Create warehouse with req: [{}] ", warehouseRequest);
        WarehouseDto warehouseDto = warehouseService.addWarehouse(warehouseRequest);
        return new ResponseEntity<>(warehouseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<WarehouseDto> getByUuid(@PathVariable String uuid) {
        log.info("Enter WarehouseController -> getWarehouseByUuid() [UUID:{}] ", uuid);
        var warehouse = warehouseService.findByUuid(uuid);
        var warehouseDto = ModelMapper.fromWarehouseToWarehouseDto(warehouse);
        return new ResponseEntity<>(warehouseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<WarehouseDto>> getAll(@ApiIgnore Pageable pageable) {
        log.info("Enter WarehouseController -> getAllWarehouses()");
        Page<WarehouseDto> allWarehouses = warehouseService.getAll(pageable);
        return new ResponseEntity<>(allWarehouses, HttpStatus.OK);
    }

    /**
     * Search for nearest Warehouses according to given postcode.
     *
     * @param postcode
     * @return ResponseEntity with a <code>List<WarehouseDto></code> of ordered warehouses.
     * @author Pawel Konarzewski
     */
    @GetMapping("/{postcode}/ordered-by-distance")
    public ResponseEntity<List<WarehouseDto>> getOrderedByDistanceFromPostcode(@PathVariable String postcode) {
        log.info("Enter PostcodeController -> getNearestWarehouses() with: " + postcode);
        List<WarehouseDto> orderedByDistanceFromPostcode = warehouseService.getOrderedByDistanceFromPostcode(postcode);
        return new ResponseEntity<>(orderedByDistanceFromPostcode, HttpStatus.OK);
    }

    @ApiPageable
    @GetMapping("/{uuid}/available-rooms")
    public ResponseEntity<Page<StorageRoomDto>> getAvailableByWarehouseUuid(@PathVariable String uuid,
                                                                            @ApiIgnore Pageable pageable) {
        log.info("Find all available storage rooms for Warehouse with UUID:[{}]", uuid);
        Page<StorageRoomDto> availableRooms = storageRoomService.getAvailableByWarehouseUuid(uuid, pageable);
        return new ResponseEntity<>(availableRooms, HttpStatus.OK);
    }

}
