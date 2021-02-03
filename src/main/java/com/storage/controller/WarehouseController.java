package com.storage.controller;

import com.storage.model.dto.DirectorDto;
import com.storage.model.dto.WarehouseDto;
import com.storage.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseDto> addWarehouse(@RequestBody WarehouseDto warehouseDto) {
        return ResponseEntity.ok(warehouseService.addWarehouse(warehouseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDto> getOneWarehouseById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getOneWarehouseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDto> updateWarehouseDirector(@RequestBody DirectorDto directorDto,@PathVariable Long id ){
        return ResponseEntity.ok(warehouseService.updateWarehouseDirector(directorDto, id));
    }
}
