package com.storage.service;

import com.storage.exceptions.ResourceNotFoundException;
import com.storage.exceptions.WarehouseServiceException;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.mapper.ModelMapper;
import com.storage.models.dto.WarehouseDto;
import com.storage.repositories.StorageRoomRepository;
import com.storage.repositories.WarehouseRepository;
import com.storage.utils.Util;
import com.storage.validators.WarehouseDtoValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.storage.models.mapper.ModelMapper.fromWarehouseToWarehouseDto;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class WarehouseService {

    public static final String WAREHOUSE = "Warehouse";
    public static final String ID = "id";

    private final WarehouseRepository warehouseRepository;
    private final StorageRoomRepository storageRoomRepository;

    public WarehouseDto addWarehouse(WarehouseDto warehouseDto) {
        log.info("Enter WarehouseService -> addWarehouse() with: " + warehouseDto);
        isWarehouseDtoValid(warehouseDto);
        var warehouse = ModelMapper.fromWarehouseDtoToWarehouse(warehouseDto);
        var address = warehouseDto.getAddress();

        var list = Util.createStorageRoomsList();
        storageRoomRepository.saveAll(list);
        warehouse.setStorageRooms(list);
        warehouse.setAddress(address);
        log.info("Warehouse: " + warehouse);
        var addedWarehouse = warehouseRepository.save(warehouse);
        return fromWarehouseToWarehouseDto(addedWarehouse);
    }

    private void isWarehouseDtoValid(WarehouseDto warehouseDto) {
        var validator = new WarehouseDtoValidator();
        var errors = validator.validate(warehouseDto);
        if (!errors.isEmpty()) {
            throw new WarehouseServiceException("Invalid WarehouseDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(",")));
        }
    }

    public WarehouseDto getWarehouseById(Long id) {
        log.info("Enter WarehouseService -> addWarehouse() with id: " + id);
        var warehouse = warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(WAREHOUSE, ID, id));
        return fromWarehouseToWarehouseDto(warehouse);
    }

    public List<WarehouseDto> getAllWarehouses() {
        log.info("Enter WarehouseService -> getAllWarehouses()");
        return ModelMapper.fromWarehouseListToWarehouseDtoList(warehouseRepository.findAll());
    }

    public List<StorageRoomDto> getNotReservedStorageRoomsByWarehouseId(Long id) {
        log.info("Enter WarehouseService -> getAvailableStorageRoomsByWarehouseId() with id: " + id);
        var warehouse = warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(WAREHOUSE, ID, id));
        return warehouse.getStorageRooms()
                .stream()
                .filter(storage -> !storage.isReserved())
                .map(ModelMapper::fromStorageRoomToStorageRoomDto)
                .collect(Collectors.toList());
    }
}
