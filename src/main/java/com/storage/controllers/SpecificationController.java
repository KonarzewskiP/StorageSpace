package com.storage.controllers;

import com.storage.models.WarehouseSpec;
import com.storage.models.enums.SpecType;
import com.storage.service.SpecificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/spec")
public class SpecificationController {

    private final SpecificationService specificationService;

    @GetMapping("/{specType}")
    public ResponseEntity<WarehouseSpec> getSpec(@PathVariable SpecType specType) {
        log.info("Enter SpecificationController -> getSpec() of: " + specType);
        return new ResponseEntity<>(specificationService.getSpec(specType), HttpStatus.OK);
    }
}
