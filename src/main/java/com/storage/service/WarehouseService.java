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
import java.util.Optional;
import java.util.stream.Collectors;

import static com.storage.constants.AppConstants.ID;
import static com.storage.constants.AppConstants.WAREHOUSE;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final DirectorRepository directorRepository;

    public WarehouseDto addWarehouse(WarehouseDto warehouseDto) {
        log.info("Enter WarehouseService -> addWarehouse() with: " + warehouseDto);
        WarehouseDtoValidator validator = new WarehouseDtoValidator();
        var errors = validator.validate(warehouseDto);
        if (!errors.isEmpty()) {
            throw new WarehouseServiceException("Invalid Warehouse!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(",")));
        }
        Warehouse warehouse = ModelMapper.fromWarehouseDtoToWarehouse(warehouseDto);
        log.info("Warehouse: " + warehouse);
        warehouseRepository.save(warehouse);
        warehouseDto.setId(warehouse.getId());
        return warehouseDto;
    }

    public WarehouseDto getOneWarehouseById(Long id) {
        log.info("Enter WarehouseService -> addWarehouse() with id: " + id);
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(WAREHOUSE, ID, id));
        return ModelMapper.fromWarehouseToWarehouseDto(warehouse);

    }

    public WarehouseDto updateWarehouseDirector(DirectorDto directorDto, Long id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new IllegalStateException("Fail to get warehouse by id: " + id));
        Director director = directorRepository.findById(directorDto.getId()).orElseThrow(() -> new IllegalStateException("Fail to get director by id: " + directorDto.getId()));
        log.info("Enter WarehouseService -> updateWarehouseDirector() with warehouse: " + warehouse);
        log.info("Enter WarehouseService -> updateWarehouseDirector() with director: " + director);
        director.getWarehouses().add(warehouse);
        warehouse.setDirector(director);
        return ModelMapper.fromWarehouseToWarehouseDto(warehouse);

    }
}
