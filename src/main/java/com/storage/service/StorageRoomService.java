package com.storage.service;

import com.storage.exceptions.ResourceNotFoundException;
import com.storage.exceptions.StorageRoomException;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.mapper.ModelMapper;
import com.storage.repositories.StorageRoomRepository;
import com.storage.validators.StorageRoomDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static com.storage.models.mapper.ModelMapper.fromStorageRoomToStorageRoomDto;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StorageRoomService {

    public static final String STORAGE_ROOM = "StorageRoom";
    public static final String ID = "id";

    private final StorageRoomRepository storageRoomRepository;

    public StorageRoomDto updateStorageRoom(StorageRoomDto storageRoomDto) {
        log.info("Enter StorageRoomService -> updateStorageRoom() with: " + storageRoomDto);
        isStorageRoomDtoValid(storageRoomDto);
        var storageRoom =
                storageRoomRepository
                        .findById(storageRoomDto.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(STORAGE_ROOM, ID, storageRoomDto.getId()));

        if (storageRoomDto.getReserved()){
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

    public StorageRoomDto findStorageRoomById(Long id) {
        log.info("Enter StorageRoomService -> findStorageRoomById() with: " + id);
        var storageRoom =
                storageRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STORAGE_ROOM, ID, id));
        return ModelMapper.fromStorageRoomToStorageRoomDto(storageRoom);
    }
}




























