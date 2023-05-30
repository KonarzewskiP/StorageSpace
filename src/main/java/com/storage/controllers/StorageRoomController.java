package com.storage.controllers;


import com.storage.models.StorageRoom;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.requests.CreateStorageRoomsRequest;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.service.StorageRoomService;
import com.storage.utils.mapper.ModelMapper;
import com.storage.utils.mapper.StorageRoomMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/storage-rooms")
public class StorageRoomController {

    private final StorageRoomService storageRoomService;
    private final StorageRoomMapper storageRoomMapper;

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
    public ResponseEntity<StorageRoomDto> updateByUuid(@PathVariable String uuid, @RequestBody @Valid StorageUpdateRequest request) {
        StorageRoom storage = storageRoomService.update(uuid, request);
        StorageRoomDto storageDto = storageRoomMapper.map(storage);
        return new ResponseEntity<>(storageDto, HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<List<StorageRoomDto>> create(@RequestBody @Valid CreateStorageRoomsRequest request) {
        List<StorageRoom> newStorageRoomList = storageRoomService.create(request);
        List<StorageRoomDto> newStorageRoomDto = newStorageRoomList.stream()
                .map(storageRoomMapper::map)
                .collect(Collectors.toList());
        return new ResponseEntity<>(newStorageRoomDto, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<Integer> delete(@RequestBody List<String> uuids) {
        int numberOfDeletedRooms = storageRoomService.delete(uuids);
        return new ResponseEntity<>(numberOfDeletedRooms, HttpStatus.OK);
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
}
