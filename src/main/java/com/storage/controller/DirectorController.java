package com.storage.controller;

import com.storage.model.dto.DirectorDto;
import com.storage.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorService directorService;

    @PostMapping
    public ResponseEntity<DirectorDto> addDirector(@RequestBody DirectorDto directorDto) {
        return ResponseEntity.ok(directorService.addDirector(directorDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDto> getOneDirectorById(@PathVariable Long id) {
        return ResponseEntity.ok(directorService.getOneDirectorById(id));
    }
}
