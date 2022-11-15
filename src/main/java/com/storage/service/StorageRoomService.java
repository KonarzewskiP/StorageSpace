package com.storage.service;

import com.storage.exceptions.StorageRoomException;
import com.storage.models.StorageRoom;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.requests.StorageRoomUpdateRequest;
import com.storage.repositories.StorageRoomRepository;
import com.storage.validators.StorageRoomUpdateReqValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.storage.models.mapper.ModelMapper.fromStorageRoomToStorageRoomDto;

@Slf4j
@Service
public class StorageRoomService extends AbstractService<StorageRoom> {
    private final StorageRoomRepository storageRoomRepository;

    public StorageRoomService(StorageRoomRepository storageRoomRepository) {
        super(StorageRoom.class, storageRoomRepository);
        this.storageRoomRepository = storageRoomRepository;
    }


    public StorageRoomDto updateStorageRoom(String uuid, StorageRoomUpdateRequest request) {
        isUpdateRequestValid(request);

        var storageRoom = findByUuidForUpdate(uuid);

        if (storageRoomDto.getReserved()) {
            storageRoom.setStartDate(storageRoomDto.getStartDate());
            storageRoom.setEndDate(storageRoomDto.getEndDate());
        }
        storageRoom.setReserved(storageRoomDto.getReserved());

        log.info("StorageRoom: " + storageRoom);
        var addedStorageRoom = storageRoomRepository.save(storageRoom);
        return fromStorageRoomToStorageRoomDto(addedStorageRoom);
    }

    private void isUpdateRequestValid(StorageRoomUpdateRequest request) {
        var validator = new StorageRoomUpdateReqValidator();
        var errors = validator.validate(request);

        if (!errors.isEmpty()) {
            throw new StorageRoomException("Invalid StorageRoomDto! errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }

    }
}




























