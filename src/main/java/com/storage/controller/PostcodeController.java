package com.storage.controller;

import com.storage.service.postcodes_api.PostcodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/postcodes")
public class PostcodeController {

    private final PostcodeService postcodeService;

    @GetMapping
    public ResponseEntity<Boolean> isPostcodeValid(@RequestParam String postCode){
        log.info("Enter PostcodeController -> isPostcodeValid() with {}",postCode);
        return new ResponseEntity<>(postcodeService.isValid(postCode), HttpStatus.OK);
    }
}
