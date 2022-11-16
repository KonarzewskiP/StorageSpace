package com.storage.controllers;

import com.storage.models.dto.postcode.PostcodeValidateDTO;
import com.storage.service.PostcodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/{postcode}/valid")
    public ResponseEntity<PostcodeValidateDTO> isValid(@PathVariable String postcode) {
        var isPostcodeValid = postcodeService.isValid(postcode);
        return new ResponseEntity<>(isPostcodeValid, HttpStatus.OK);
    }
}
