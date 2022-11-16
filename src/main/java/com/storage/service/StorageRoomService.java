package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.models.StorageRoom;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.repositories.StorageRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class StorageRoomService extends AbstractService<StorageRoom> {
    private final StorageRoomRepository storageRoomRepository;

    public StorageRoomService(StorageRoomRepository storageRoomRepository) {
        super(StorageRoom.class, storageRoomRepository);
        this.storageRoomRepository = storageRoomRepository;
    }

    public StorageRoom updateStorageRoom(String uuid, StorageUpdateRequest request) {
        log.info("Updating warehouse with uuid [{}] ", request);
        isUpdateRequestValid(request);
        var storage = findByUuidForUpdate(uuid);

        if (request.getStatus() != storage.getStatus())
            storage.setStatus(request.getStatus());

        if (request.getStorageSize() != storage.getStorageSize())
            storage.setStorageSize(request.getStorageSize());

        storage = storageRoomRepository.save(storage);

        log.info("Updated warehouse with uuid: [{}]", storage.getUuid());
        return storage;
    }

    private void isUpdateRequestValid(StorageUpdateRequest request) {
        if (request == null)
            throw new BadRequestException("Storage Update Request is NULL!");
    }

}




























