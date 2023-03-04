package com.storage.controllers;

import com.storage.models.dto.WarehouseDto;
import com.storage.models.mapper.ModelMapper;
import com.storage.models.requests.CreateWarehouseRequest;
import com.storage.service.WarehouseService;
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
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseDto> createWarehouse(@RequestBody @Valid CreateWarehouseRequest warehouseRequest) {
        log.info("Create warehouse with req: [{}] ", warehouseRequest);
        return new ResponseEntity<>(warehouseService.addWarehouse(warehouseRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<WarehouseDto> getWarehouseByUuid(@PathVariable String uuid) {
        log.info("Enter WarehouseController -> getWarehouseByUuid() [UUID:{}] ", uuid);
        var warehouse = warehouseService.findByUuid(uuid);
        var warehouseDto = ModelMapper.fromWarehouseToWarehouseDto(warehouse);
        return new ResponseEntity<>(warehouseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<WarehouseDto>> getAllWarehouses(@ApiIgnore Pageable pageable) {
        log.info("Enter WarehouseController -> getAllWarehouses()");
        return new ResponseEntity<>(warehouseService.getAllWarehouses(pageable), HttpStatus.OK);
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
