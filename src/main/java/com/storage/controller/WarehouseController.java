package com.storage.controller;

import com.storage.model.dto.DirectorDto;
import com.storage.model.dto.WarehouseDto;
import com.storage.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseDto> addWarehouse(@RequestBody WarehouseDto warehouseDto) {
        log.info("Enter WarehouseController -> addWarehouse() with: " + warehouseDto);
        return ResponseEntity.ok(warehouseService.addWarehouse(warehouseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDto> updateWarehouseDirector(@RequestBody DirectorDto directorDto,@PathVariable Long id ){
        log.info("Enter WarehouseController -> updateWarehouseDirector() with director: " + directorDto);
        log.info("Enter WarehouseController -> updateWarehouseDirector() with warehouse id: " + id);
        return ResponseEntity.ok(warehouseService.updateWarehouseDirector(directorDto, id));
    }
}
