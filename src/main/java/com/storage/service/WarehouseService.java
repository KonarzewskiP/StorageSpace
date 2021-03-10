package com.storage.service;

import com.storage.exception.ResourceNotFoundException;
import com.storage.exception.WarehouseServiceException;
import com.storage.model.Warehouse;
import com.storage.model.dto.StorageRoomDto;
import com.storage.model.mapper.ModelMapper;
import com.storage.model.dto.WarehouseDto;
import com.storage.model.postcodes_api.response.ResultSingleResponse;
import com.storage.repository.StorageRoomRepository;
import com.storage.repository.WarehouseRepository;
import com.storage.service.postcodes_api.PostcodeService;
import com.storage.utils.Util;
import com.storage.validator.WarehouseDtoValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.storage.constants.AppConstants.*;
import static com.storage.model.mapper.ModelMapper.fromWarehouseToWarehouseDto;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final StorageRoomRepository storageRoomRepository;
    private final PostcodeService postcodeService;

    public WarehouseDto addWarehouse(WarehouseDto warehouseDto) {
        log.info("Enter WarehouseService -> addWarehouse() with: " + warehouseDto);
        var validator = new WarehouseDtoValidator();
        var errors = validator.validate(warehouseDto);
        if (!errors.isEmpty()) {
            throw new WarehouseServiceException("Invalid WarehouseDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(",")));
        }
        var warehouse = ModelMapper.fromWarehouseDtoToWarehouse(warehouseDto);

        var list = Util.createStorageRoomsList();
        storageRoomRepository.saveAll(list);
        warehouse.setStorageRooms(list);

        log.info("Warehouse: " + warehouse);
        var addedWarehouse = warehouseRepository.save(warehouse);
        return fromWarehouseToWarehouseDto(addedWarehouse);
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
