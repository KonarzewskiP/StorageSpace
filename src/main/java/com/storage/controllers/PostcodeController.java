package com.storage.controllers;

import com.storage.models.dto.WarehouseDto;
import com.storage.models.dto.externals.postcode.PostcodeValidationResponse;
import com.storage.service.postcodes_api.PostcodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/postcodes")
public class PostcodeController {
    private final PostcodeService postcodeService;


    /**
     * The method that calls API and returns Boolean object.
     * <p>
     * Params: postcode - postcode from the UK
     * Returns: HttpResponse 200 and Boolean true if postcode is valid,
     *
     * @author Pawel Konarzewski
     */
    @GetMapping("/{postcode}")
    public ResponseEntity<PostcodeValidationResponse> isValid(@PathVariable String postcode) {
        log.info("Enter PostcodeController -> isPostcodeValid() with {}", postcode);
        return new ResponseEntity<>(postcodeService.isValid(postcode), HttpStatus.OK);
    }
    /**
     * Method that search for nearest Warehouses according to given postcode.
     *
     * @param postcode
     * @return ResponseEntity with a <code>List<WarehouseDto></code> of ordered warehouses.
     * @author Pawel Konarzewski
     */
    @GetMapping("/{postcode}/nearest")
    public ResponseEntity<List<WarehouseDto>> getNearestWarehouses(@PathVariable String postcode) {
        log.info("Enter PostcodeController -> getNearestWarehouses() with: " + postcode);
        return new ResponseEntity<>(postcodeService.getOrderedWarehousesByDistanceFromPostcode(postcode), HttpStatus.OK);
    }
}
