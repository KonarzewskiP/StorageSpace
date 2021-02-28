package com.storage.service;

import com.storage.exception.ResourceNotFoundException;
import com.storage.exception.WarehouseServiceException;
import com.storage.model.Director;
import com.storage.model.mapper.ModelMapper;
import com.storage.model.Warehouse;
import com.storage.model.dto.DirectorDto;
import com.storage.model.dto.WarehouseDto;
import com.storage.repository.DirectorRepository;
import com.storage.repository.WarehouseRepository;
import com.storage.validator.WarehouseDtoValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static com.storage.constants.AppConstants.*;
import static com.storage.model.mapper.ModelMapper.fromWarehouseToWarehouseDto;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final DirectorRepository directorRepository;

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
        log.info("Warehouse: " + warehouse);
        var addedWarehouse = warehouseRepository.save(warehouse);
        return fromWarehouseToWarehouseDto(addedWarehouse);
    }

    public WarehouseDto getWarehouseById(Long id) {
        log.info("Enter WarehouseService -> addWarehouse() with id: " + id);
        var warehouse = warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(WAREHOUSE, ID, id));
        return fromWarehouseToWarehouseDto(warehouse);
    }

    public WarehouseDto updateWarehouseDirector(DirectorDto directorDto, Long id) {
        var warehouse =
                warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(WAREHOUSE, ID, id));
        var director =
                directorRepository.findById(directorDto.getId()).orElseThrow(() -> new ResourceNotFoundException(DIRECTOR, ID, id));
        log.info("Enter WarehouseService -> updateWarehouseDirector() with warehouse: " + warehouse);
        log.info("Enter WarehouseService -> updateWarehouseDirector() with director: " + director);
        director.getWarehouses().add(warehouse);
        warehouse.setDirector(director);
        return fromWarehouseToWarehouseDto(warehouse);
    }
}
