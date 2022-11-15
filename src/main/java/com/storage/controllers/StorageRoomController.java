package com.storage.controllers;


import com.storage.models.dto.StorageRoomDto;
import com.storage.models.mapper.ModelMapper;
import com.storage.models.requests.StorageRoomUpdateRequest;
import com.storage.service.StorageRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/storage-rooms")
public class StorageRoomController {

    private final StorageRoomService storageRoomService;

    /**
     * Updates StorageRoom entity
     * <p>
     * Params: StorageRoomDto object with information to update.
     * Returns: ResponseEntity with <code>StorageRoomDto</code> object
     *
     * @author Pawel Konarzewski
     */
    @Transactional
    @PutMapping("/{uuid}")
    public ResponseEntity<StorageRoomDto> updateByUuid(@PathVariable String uuid, @RequestBody StorageRoomUpdateRequest request) {
        log.info("Enter StorageRoomController -> updateStorageRoomByWarehouseId() with: [UUID:{}], [request:{}] ", uuid, request);
        var storageRoomDto = storageRoomService.updateStorageRoom(uuid, request);
        return new ResponseEntity<>(storageRoomDto, HttpStatus.OK);
    }

    /**
     * The method that searches for StorageRoom by id
     * <p>
     * Params: StorageRoom id.
     * Returns: ResponseEntity with <code>StorageRoomDto</code> object
     *
     * @author Pawel Konarzewski
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<StorageRoomDto> findByUuid(@PathVariable String uuid) {
        log.info("Enter StorageRoomController -> findByUuid() with [UUID:{}] ", uuid);
        var storageRoom = storageRoomService.findByUuid(uuid);
        var storageRoomDto = ModelMapper.fromStorageRoomToStorageRoomDto(storageRoom);
        return new ResponseEntity<>(storageRoomDto, HttpStatus.OK);
    }

}
