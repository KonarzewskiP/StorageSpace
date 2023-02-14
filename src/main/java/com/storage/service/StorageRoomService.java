package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.models.StorageRoom;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.mapper.ModelMapper;
import com.storage.models.requests.StorageUpdateRequest;
import com.storage.repositories.StorageRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


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

    public Page<StorageRoomDto> getByWarehouseUuidAndStatus(String warehouseUuid, StorageRoomStatus status, Pageable pageable) {
        if (status == null)
            throw new BadRequestException("Status is mandatory!");

        Long warehouseId = warehouseService.findIdByUuid(warehouseUuid);

        Specification<StorageRoom> specification =
                Specification.where((root, cq, cb) -> cb.equal(root.get("warehouseId"), warehouseId));

        specification = specification.and(Specification.where((root, cq, cb) -> cb.equal(root.get("status"), status)));


        return storageRoomRepository.findAll(specification,pageable).map(ModelMapper::fromStorageRoomToStorageRoomDto);
    }

}




























