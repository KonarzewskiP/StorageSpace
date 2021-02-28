package com.storage.service;

import com.storage.exception.StorageRoomException;
import com.storage.model.dto.StorageRoomDto;
import com.storage.model.mapper.ModelMapper;
import com.storage.repository.StorageRoomRepository;
import com.storage.validator.StorageRoomDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static com.storage.model.mapper.ModelMapper.fromStorageRoomDtoToStorageRoom;
import static com.storage.model.mapper.ModelMapper.fromStorageRoomToStorageRoomDto;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StorageRoomService {

    private final StorageRoomRepository storageRoomRepository;

    public StorageRoomDto addStorageRoom(StorageRoomDto storageRoomDto) {
        log.info("Enter StorageRoomService -> addStorageRoom() with: " + storageRoomDto);
        var validator = new StorageRoomDtoValidator();
        var errors = validator.validate(storageRoomDto);
        if (!errors.isEmpty()) {
            throw new StorageRoomException("Invalid StorageRoomDto! errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
        var storageRoom = fromStorageRoomDtoToStorageRoom(storageRoomDto);
        storageRoom.setStartDate(null);
        storageRoom.setEndDate(null);
        log.info("StorageRoom: " + storageRoom);
        var addedStorageRoom = storageRoomRepository.save(storageRoom);
        return fromStorageRoomToStorageRoomDto(addedStorageRoom);
    }

}
