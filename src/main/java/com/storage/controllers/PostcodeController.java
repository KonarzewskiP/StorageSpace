package com.storage.controllers;

import com.storage.models.dto.WarehouseDto;
import com.storage.service.postcodes_api.PostcodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/postcodes")
public class PostcodeController {

    private final PostcodeService postcodeService;

    @GetMapping("/{postcode}")
    public ResponseEntity<Boolean> isPostcodeValid(@PathVariable String postcode){
        log.info("Enter PostcodeController -> isPostcodeValid() with {}", postcode);
        return new ResponseEntity<>(postcodeService.isValid(postcode), HttpStatus.OK);
    }

    /**
     * Method that search for nearest Warehouses according to given postcode.
     *
     * @param postcode
     * @return ResponseEntity with a <code>List<WarehouseDto></code>.
     * @author Pawel Konarzewski
     * @since 04/03/2021
     */

    @GetMapping("/{postcode}/nearest")
    public ResponseEntity<List<WarehouseDto>> getNearestWarehouses(@PathVariable String postcode) {
        log.info("Enter WarehouseController -> getNearestWarehouses() with: " + postcode);
        return new ResponseEntity<>(postcodeService.getOrderedWarehousesByDistanceFromPostcode(postcode), HttpStatus.OK);
    }
}
