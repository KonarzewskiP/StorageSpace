package com.storage.controllers;

import com.storage.exceptions.BadRequestException;
import com.storage.models.requests.ValidatePostcodesRequest;
import com.storage.service.PostcodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/postcodes")
public class PostcodeController {
    private final PostcodeService postcodeService;


    /**
     * Check if postcode is valid
     * <p>
     * Params: postcode - postcode from the UK
     * Returns: HttpResponse 200 and Boolean true if postcode is valid,
     *
     * @author Pawel Konarzewski
     */
    @GetMapping("/{postcode}")
    public boolean isValid(@PathVariable String postcode) {
        log.info("Start postcode validation: {}", postcode);
        if (StringUtils.isBlank(postcode))
            throw new BadRequestException("Postcode can not be empty or null");

        var isPostcodeValid = postcodeService.isValid(postcode);
        log.info("Start postcode validation: {}", postcode);
//        return new ResponseEntity<>(isPostcodeValid, HttpStatus.OK);
        return true;
    }

    @GetMapping("/validate")
    public boolean isValidMultiple(@RequestBody ValidatePostcodesRequest request) {
        var isPostcodeValid = postcodeService.getMultipleCoordinates(request.getPostcodes());
//        return new ResponseEntity<>(isPostcodeValid, HttpStatus.OK);
        return true;
    }


}
