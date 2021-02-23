package com.storage.controller;

import com.storage.model.dto.DirectorDto;
import com.storage.service.DirectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorService directorService;

    @PostMapping
    public ResponseEntity<DirectorDto> addDirector(@RequestBody DirectorDto directorDto) {
        log.info("Enter DirectorController -> addDirector() with: " + directorDto);
        return new ResponseEntity<>(directorService.addDirector(directorDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDto> getOneDirectorById(@PathVariable Long id) {
        return new ResponseEntity<>(directorService.getOneDirectorById(id), HttpStatus.OK);
    }
}
