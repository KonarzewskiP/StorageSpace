package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.models.StorageRoom;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.requests.CreateStorageRoomsRequest;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.repositories.StorageRoomRepository;
import com.storage.utils.UuidGenerator;
import com.storage.utils.mapper.ModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StorageRoomService extends AbstractService<StorageRoom> {
    private final StorageRoomRepository storageRoomRepository;
    private final WarehouseService warehouseService;

    public StorageRoomService(StorageRoomRepository storageRoomRepository,
                              WarehouseService warehouseService) {
        super(StorageRoom.class, storageRoomRepository);
        this.storageRoomRepository = storageRoomRepository;
        this.warehouseService = warehouseService;
    }

    public StorageRoom update(String uuid, StorageUpdateRequest request) {
        log.info("Updating warehouse with uuid [{}] ", request);
        StorageRoom storage = findByUuidForUpdate(uuid);

        if (request.status() != storage.getStatus())
            storage.setStatus(request.status());

        if (request.storageSize() != storage.getStorageSize())
            storage.setStorageSize(request.storageSize());

        storage = storageRoomRepository.save(storage);

        log.info("Updated warehouse with uuid: [{}]", uuid);
        return storage;
    }

    /**
     * The method retrieves all available storage rooms to rent from the specific warehouse.
     * First retrieves the warehouse by id, if id does not exist it throws an exception. Then search
     * for all available storage rooms and map it to dto object.
     * <p>
     *
     * @param id of the warehouse to be searched for and to retrieves available storage rooms from.
     * @return List of available storage rooms with details from the specific warehouse.
     * @throws NotFoundException if the id of the warehouse does not exist.
     */

    public Page<StorageRoomDto> getAvailableByWarehouseUuid(String warehouseUuid, Pageable pageable) {
        Long warehouseId = warehouseService.findIdByUuid(warehouseUuid);

        Specification<StorageRoom> specification =
                Specification.where((root, cq, cb) -> cb.equal(root.get("warehouseId"), warehouseId));

        specification = specification.and(Specification.where((root, cq, cb) -> cb.equal(root.get("status"), StorageRoomStatus.AVAILABLE)));

        return storageRoomRepository.findAll(specification, pageable).map(ModelMapper::fromStorageRoomToStorageRoomDto);
    }

    public Page<StorageRoomDto> getAllByWarehouseUuid(String warehouseUuid, Pageable pageable) {
        Long warehouseId = warehouseService.findIdByUuid(warehouseUuid);

        Specification<StorageRoom> specification =
                Specification.where((root, cq, cb) -> cb.equal(root.get("warehouseId"), warehouseId));

        return storageRoomRepository.findAll(specification, pageable).map(ModelMapper::fromStorageRoomToStorageRoomDto);
    }

    public List<StorageRoom> create(CreateStorageRoomsRequest request) {
        log.info("Start creating multiple storage room with request: [{}]", request);

        if (request.storageRoomList().isEmpty())
            throw new BadRequestException("Can not create any new Storage Rooms because list with request is empty! request: " + request);

        Long warehouseId = warehouseService.findIdByUuid(request.warehouseUuid());

        List<StorageRoom> newStorageRoomList = request.storageRoomList()
                .stream()
                .map(req -> StorageRoom.builder()
                        .uuid(UuidGenerator.next())
                        .storageSize(req.storageSize())
                        .status(req.status())
                        .warehouseId(warehouseId)
                        .build())
                .collect(Collectors.toList());

        newStorageRoomList = storageRoomRepository.saveAll(newStorageRoomList);
        log.info("Successfully created [{}] storage rooms, for warehouse with uuid: [{}] ", newStorageRoomList.size(), request.warehouseUuid());
        return newStorageRoomList;
    }

    public int delete(List<String> uuids) {
        log.info("Start deleting storage room with uuid: [{}]", uuids);

        if (uuids == null || uuids.isEmpty())
            throw new BadRequestException("List of uuids can not be null or empty!");

        int numOfDeletedRooms = storageRoomRepository.deleteAllByUuids(uuids);

        log.info("Successfully deleted [{}] storage rooms with uuid: [{}]", numOfDeletedRooms, uuids);
        return numOfDeletedRooms;
    }

    public int deleteAllByWarehouseUuid(String uuid) {
        log.info("Start deleting storage room with uuid: [{}]", uuid);

        Long warehouseId = warehouseService.findIdByUuid(uuid);
        int numberOfDeletedRooms = storageRoomRepository.deleteAllByWarehouseId(warehouseId);

        log.info("Successfully deleted [{}] storage room for warehouse with uuid: [{}]", numberOfDeletedRooms, uuid);
        return numberOfDeletedRooms;
    }


}




























