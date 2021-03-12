package com.storage.controllers;


import com.storage.models.dto.StorageRoomDto;
import com.storage.service.StorageRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/storages")
public class StorageRoomController {

    private final StorageRoomService storageRoomService;

    @PostMapping
    public ResponseEntity<StorageRoomDto> addStorageRoom(@RequestBody StorageRoomDto storageRoomDto) {
        log.info("Enter StorageRoomController -> addStorageRoom() with: " + storageRoomDto);
        return new ResponseEntity<>(storageRoomService.addStorageRoom(storageRoomDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageRoomDto> findStorageRoomById(@PathVariable Long id) {
        log.info("Enter StorageRoomController -> findStorageRoomById() with: " + id);
        return new ResponseEntity<>(storageRoomService.findStorageRoomById(id), HttpStatus.OK);
    }

}
