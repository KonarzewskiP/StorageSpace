package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.exceptions.StorageRoomException;
import com.storage.models.StorageRoom;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.mapper.ModelMapper;
import com.storage.models.requests.StorageRoomUpdateRequest;
import com.storage.repositories.StorageRoomRepository;
import com.storage.validators.StorageRoomDtoValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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


    public StorageRoomDto updateStorageRoom(StorageRoom storageRoom, StorageRoomUpdateRequest request) {
        isStorageRoomDtoValid(storageRoomDto);
        var storageRoom =
                storageRoomRepository
                        .findById(storageRoomDto.getId())
                        .orElseThrow(() -> new NotFoundException(STORAGE_ROOM, ID, storageRoomDto.getId()));

        if (storageRoomDto.getReserved()) {
            storageRoom.setStartDate(storageRoomDto.getStartDate());
            storageRoom.setEndDate(storageRoomDto.getEndDate());
        }
        storageRoom.setReserved(storageRoomDto.getReserved());

        log.info("StorageRoom: " + storageRoom);
        var addedStorageRoom = storageRoomRepository.save(storageRoom);
        return fromStorageRoomToStorageRoomDto(addedStorageRoom);
    }

    private void isStorageRoomDtoValid(StorageRoomDto storageRoomDto) {
        var validator = new StorageRoomDtoValidator();
        var errors = validator.validate(storageRoomDto);
        if (!errors.isEmpty()) {
            throw new StorageRoomException("Invalid StorageRoomDto! errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
    }

    public StorageRoomDto findByUuid(String uuid) {
        if (StringUtils.isBlank(uuid))
            throw new BadRequestException("Uuid can not be null or empty");

        var storageRoom =
                storageRoomRepository.findByUuid(uuid)
                        .orElseThrow(() -> new NotFoundException(String.format("Storage room not found by [UUID:%s]", uuid)));

        return ModelMapper.fromStorageRoomToStorageRoomDto(storageRoom);
    }
}




























