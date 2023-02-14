package com.storage.controllers;


import com.storage.models.dto.StorageRoomDto;
import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.mapper.ModelMapper;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.service.StorageRoomService;
import com.storage.utils.annotation.ApiPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;

import static com.storage.models.mapper.ModelMapper.fromStorageRoomToStorageRoomDto;

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
    public ResponseEntity<StorageRoomDto> updateByUuid(@PathVariable String uuid, @RequestBody StorageUpdateRequest request) {
        var storage = storageRoomService.updateStorageRoom(uuid, request);
        var storageDto = fromStorageRoomToStorageRoomDto(storage);
        return new ResponseEntity<>(storageDto, HttpStatus.OK);
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
        var storageRoom = storageRoomService.findByUuid(uuid);
        var storageRoomDto = ModelMapper.fromStorageRoomToStorageRoomDto(storageRoom);
        return new ResponseEntity<>(storageRoomDto, HttpStatus.OK);
    }

    @ApiPageable
    @GetMapping("/{warehouseUuid}/available")
    public ResponseEntity<Page<StorageRoomDto>> getAvailableByWarehouseUuid(@PathVariable String warehouseUuid,
                                                                            @ApiIgnore Pageable pageable) {
        log.info("Find all available storage rooms in Warehouse with UUID:[{}]", warehouseUuid);
        return new ResponseEntity<>(storageRoomService.getByWarehouseUuidAndStatus(warehouseUuid, StorageRoomStatus.AVAILABLE, pageable), HttpStatus.OK);
    }

}
