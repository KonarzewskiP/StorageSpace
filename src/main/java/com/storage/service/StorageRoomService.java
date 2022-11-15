package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.models.StorageRoom;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.repositories.StorageRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.storage.models.mapper.ModelMapper.fromStorageRoomToStorageRoomDto;

@Slf4j
@Service
public class StorageRoomService extends AbstractService<StorageRoom> {
    private final StorageRoomRepository storageRoomRepository;

    public StorageRoomService(StorageRoomRepository storageRoomRepository) {
        super(StorageRoom.class, storageRoomRepository);
        this.storageRoomRepository = storageRoomRepository;
    }

    public StorageRoomDto updateStorageRoom(String uuid, StorageUpdateRequest request) {
        isUpdateRequestValid(request);
        var storage = findByUuidForUpdate(uuid);

        if (request.getStatus() != storage.getStatus())
            storage.setStatus(request.getStatus());

        if (request.getStorageSize() != storage.getStorageSize())
            storage.setStorageSize(request.getStorageSize());

        var updatedStorage = storageRoomRepository.save(storage);
        return fromStorageRoomToStorageRoomDto(updatedStorage);
    }

    private void isUpdateRequestValid(StorageUpdateRequest request) {
        if (request == null)
            throw new BadRequestException("Storage Update Request is NULL!");
    }

}




























