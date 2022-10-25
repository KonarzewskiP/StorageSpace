package com.storage.controllers;

import com.storage.models.WarehouseSpec;
import com.storage.models.enums.SpecType;
import com.storage.service.SpecificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pawel Konarzewski
 * @version 1.0
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/spec")
public class SpecificationController {

    private final SpecificationService specificationService;

    /**
     * Returns different types of warehouse specifications.
     * Each warehouse has own specification and depending on this specification,
     * customers can have different services.
     * <p>
     * @param specType enum with information to update.
     * @return ResponseEntity with <code>WarehouseSpec</code> object.
     */

    @GetMapping("/{specType}")
    public ResponseEntity<WarehouseSpec> getSpec(@PathVariable SpecType specType) {
        log.info("Enter SpecificationController -> getSpec() of: " + specType);
        return new ResponseEntity<>(specificationService.getSpec(specType), HttpStatus.OK);
    }
}
