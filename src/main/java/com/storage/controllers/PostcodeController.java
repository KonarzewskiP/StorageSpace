package com.storage.controllers;

import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeValidateDTO;
import com.storage.service.PostcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/postcodes")
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
        PostcodeValidateDTO postcodeValidateDTO = postcodeService.isValid(postcode);
        return new ResponseEntity<>(postcodeValidateDTO, HttpStatus.OK);
    }

    @GetMapping("/{postcode}/details")
    public ResponseEntity<PostcodeDTO> getDetails(@PathVariable String postcode) {
        PostcodeDTO postcodeDTO = postcodeService.getDetails(postcode);
        return new ResponseEntity<>(postcodeDTO, HttpStatus.OK);
    }
}
